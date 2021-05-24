package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Level1Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.navigationSettings

class Navigation(val stateManager : StateManager) {

    init {
        var startScreenIdentifier = navigationSettings.homeScreen.screenIdentifier
        if (navigationSettings.saveLastLevel1Screen) {
            startScreenIdentifier = ScreenIdentifier.getByURI(dataRepository.localSettings.savedLevel1URI) ?: startScreenIdentifier
        }
        navigateByScreenIdentifier(startScreenIdentifier)
    }

    lateinit var title : String

    val dataRepository
        get() = stateManager.dataRepository

    val currentScreenIdentifier : ScreenIdentifier
        get() = stateManager.currentScreenIdentifier

    val only1ScreenInBackstack : Boolean
        get() = stateManager.only1ScreenInBackstack

    val navigationLevelsMap : Map<Int,ScreenIdentifier>
        get() = stateManager.navigationLevelsMap

    // used by the Router composable in Compose apps
    // it returns a list of screens whose state has been removed, so they should also be removed from Compose's SaveableStateHolder
    val screenStatesToRemove : List<ScreenIdentifier>
        get() = stateManager.getScreenStatesToRemove()

    // used by the Router view in SwiftUI apps
    // it returns a list of UI screens to be rendered inside a SwiftUI's ZStack (it only includes screens whose state is stored, not the full backstack)
    val statefulBackstack : List<UIBackstackEntry>
        get() = stateManager.getStatefulBackstack()


    fun navigate(screen: Screen, params: ScreenParams? = null) {
        navigateByScreenIdentifier(ScreenIdentifier.get(screen,params))
    }

    fun navigateByLevel1Menu(level1NavigationItem: Level1Navigation) {
        navigateByScreenIdentifier(level1NavigationItem.screenIdentifier)
    }

    fun navigateByScreenIdentifier(screenIdentifier: ScreenIdentifier) {
        debugLogger.log("navigate to /"+screenIdentifier.URI)
        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        val previousScreen = stateManager.getCurrentScreen()
        title = screenInitSettings.title
        stateManager.addScreen(screenIdentifier, screenInitSettings.initState(screenIdentifier))
        if (previousScreen != screenIdentifier.screen  || !screenInitSettings.skipFirstRecompositionIfSameAsPreviousScreen) {
            stateManager.triggerRecomposition() // FIRST UI RECOMPOSITION
        }
        stateManager.runInCurrentScreenScope {
            screenInitSettings.callOnInit(stateManager) // SECOND UI RECOMPOSITION
        }
        if (navigationSettings.saveLastLevel1Screen && screenIdentifier.screen.navigationLevel == 1) {
            dataRepository.localSettings.savedLevel1URI = screenIdentifier.URI
        }
    }

    fun exitScreen() {
        debugLogger.log("exitScreen")
        stateManager.removeLastScreen()
        val screenInitSettings = currentScreenIdentifier.getScreenInitSettings(this)
        title = screenInitSettings.title
        if (stateManager.isInTheStatesMap(currentScreenIdentifier)) {
            // if state is already stored, just trigger a recomposition
            stateManager.triggerRecomposition()
        } else {
            // if state is not stored, navigate to it, so that it reinitializes the state
            // (it always checks what's the latest URI in the backstack, so that it won't add an extra backstack element)
            navigateByScreenIdentifier(currentScreenIdentifier)
        }
    }


    fun onReEnterForeground() {
        // not called at app startup, but only when reentering the app after it was in background
        debugLogger.log("onReEnterForeground: recomposition is triggered")
        val reinitializedScreens = stateManager.reinitScreenScopes()
        stateManager.triggerRecomposition()
        reinitializedScreens.forEach {
            it.getScreenInitSettings(this).apply {
                if (callOnInitAlsoAfterBackground) {
                    stateManager.runInCurrentScreenScope { callOnInit(stateManager) }
                }
            }
        }
    }

    fun onEnterBackground() {
        debugLogger.log("onEnterBackground: screen scopes are cancelled")
        stateManager.cancelScreenScopes()
    }

    fun onChangeOrientation() {
        debugLogger.log("onChangeOrientation: recomposition is triggered")
        stateManager.triggerRecomposition()
    }


}
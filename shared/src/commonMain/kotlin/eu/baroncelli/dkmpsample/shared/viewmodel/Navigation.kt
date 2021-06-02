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
        navigateByScreenIdentifier(startScreenIdentifier, false)
    }

    val stateProvider by lazy { StateProvider(stateManager) }
    val events by lazy { Events(stateManager) }

    fun getTitle(screenIdentifier: ScreenIdentifier) : String {
        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        return screenInitSettings.title
    }

    val dataRepository
        get() = stateManager.dataRepository

    val currentScreenIdentifier : ScreenIdentifier
        get() = stateManager.currentScreenIdentifier

    val currentLevel1ScreenIdentifier : ScreenIdentifier
        get() = stateManager.currentLevel1ScreenIdentifier

    val only1ScreenInBackstack : Boolean
        get() = stateManager.only1ScreenInBackstack


    // used by the Router composable in Compose apps
    // it returns a list of screens whose state has been removed, so they should also be removed from Compose's SaveableStateHolder
    val screenStatesToRemove : List<ScreenIdentifier>
        get() = stateManager.getScreenStatesToRemove()

    // used by the Router view in SwiftUI apps
    // it returns a list of UI screens to be rendered inside a SwiftUI's ZStack (it only includes screens whose state is stored, not the full backstack)
    val level1Backstack : List<level1BackstackEntry>
        get() = stateManager.getLevel1BackstackEntriesList()

    fun getNavigationLevelsMap(level1ScreenIdentifier: ScreenIdentifier) : Map<Int,ScreenIdentifier>? {
        return stateManager.verticalNavigationLevels[level1ScreenIdentifier.URI]
    }

    fun navigate(screen: Screen, params: ScreenParams? = null) {
        navigateByScreenIdentifier(ScreenIdentifier.get(screen,params), true)
    }

    fun navigateByLevel1Menu(level1NavigationItem: Level1Navigation) {
        val navigationLevelsMap = getNavigationLevelsMap(level1NavigationItem.screenIdentifier)
        if (navigationLevelsMap==null) {
            debugLogger.log("navigationLevelsMap: null")
            navigateByScreenIdentifier(level1NavigationItem.screenIdentifier, true)
        } else {
            debugLogger.log("navigationLevelsMap: size "+navigationLevelsMap.size)
            navigationLevelsMap.keys.sorted().forEach {
                navigateByScreenIdentifier(navigationLevelsMap[it]!!, true)
            }
        }

    }

    fun navigateByScreenIdentifier(screenIdentifier: ScreenIdentifier, triggerRecomposition: Boolean) {
        debugLogger.log("navigate to /"+screenIdentifier.URI)
        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        val skipInitState = stateManager.isInTheStatesMap(screenIdentifier) && !screenInitSettings.reinitOnEachNavigation
        stateManager.addScreen(screenIdentifier, screenInitSettings, skipInitState)
        if (triggerRecomposition) {
            stateManager.triggerRecomposition() // FIRST UI RECOMPOSITION
        }
        if (!skipInitState) {
            stateManager.runInScreenScope(screenIdentifier) {
                screenInitSettings.callOnInit(stateManager) // SECOND UI RECOMPOSITION
            }
        }
        if (navigationSettings.saveLastLevel1Screen && screenIdentifier.screen.navigationLevel == 1) {
            dataRepository.localSettings.savedLevel1URI = screenIdentifier.URI
        }
    }

    fun exitScreen(triggerRecomposition: Boolean) {
        debugLogger.log("exitScreen")
        stateManager.removeLastScreen()
        if (triggerRecomposition) {
            if (stateManager.isInTheStatesMap(currentScreenIdentifier)) {
                // if state is already stored, just trigger a recomposition
                stateManager.triggerRecomposition()
            } else {
                // if state is not stored, navigate to it, so that it reinitializes the state
                // (it always checks what's the latest URI in the backstack, so that it won't add an extra backstack element)
                navigateByScreenIdentifier(currentScreenIdentifier, true)
            }
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
                    stateManager.runInScreenScope { callOnInit(stateManager) }
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
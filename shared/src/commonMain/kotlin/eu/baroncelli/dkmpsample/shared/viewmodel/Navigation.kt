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

    val dataRepository
        get() = stateManager.dataRepository

    val currentScreenIdentifier : ScreenIdentifier
        get() = stateManager.currentScreenIdentifier

    fun getScreenUIsToForget() : List<ScreenIdentifier> {
        val screenUIsToForget = stateManager.lastRemovedScreens.toList()
        stateManager.lastRemovedScreens.clear()
        return screenUIsToForget
    }

    fun navigate(screen: Screen, params: ScreenParams? = null) {
        navigateByScreenIdentifier(ScreenIdentifier(screen,params))
    }

    fun navigateByLevel1Menu(level1NavigationItem: Level1Navigation) {
        navigateByScreenIdentifier(level1NavigationItem.screenIdentifier)
    }

    fun navigateByScreenIdentifier(screenIdentifier: ScreenIdentifier) {
        debugLogger.log("navigate to /"+screenIdentifier.URI)
        var shouldTriggerRecomposition = true
        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        if (stateManager.level1Backstack.isNotEmpty()) {
            if (currentScreenIdentifier.screen == screenIdentifier.screen && screenInitSettings.skipFirstRecompositionIfSameAsPreviousScreen) {
                shouldTriggerRecomposition = false
            }
        }
        stateManager.addScreen(screenIdentifier, screenInitSettings.initState(screenIdentifier))
        if (shouldTriggerRecomposition) {
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
        stateManager.triggerRecomposition()
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


}
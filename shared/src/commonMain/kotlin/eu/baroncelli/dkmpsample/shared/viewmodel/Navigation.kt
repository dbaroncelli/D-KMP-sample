package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.ScreenInitSettings
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.navigationSettings

class Navigation(val stateManager : StateManager) {

    init {
        val homescreenIdentifier = navigationSettings.homeScreen.screenIdentifier
        navigate(homescreenIdentifier.screen, homescreenIdentifier.params)
    }

    val dataRepository
        get() = stateManager.dataRepository

    val screenUIsToForget = mutableListOf<ScreenIdentifier>()

    val currentScreenIdentifier : ScreenIdentifier
        get() = stateManager.currentScreenIdentifier

    fun navigate(screen: Screen, params: ScreenParams? = null) {
        val screenIdentifier = ScreenIdentifier(screen,params)
        debugLogger.log("navigate to /"+screenIdentifier.URI)
        var shouldTriggerRecomposition = true
        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        if (stateManager.backstack.isNotEmpty()) {
            if (currentScreenIdentifier.screen == screenIdentifier.screen && screenInitSettings.skipFirstRecompositionIfSameAsPreviousScreen) {
                shouldTriggerRecomposition = false
            }
            removeScreensIfNeeded(screenIdentifier)
        }
        stateManager.addScreen(screenIdentifier, screenInitSettings.initState(screenIdentifier))
        if (shouldTriggerRecomposition) {
            stateManager.triggerRecomposition()
        }
        stateManager.runInCurrentScreenScope {
            screenInitSettings.callOnInit(stateManager)
        }
        if (navigationSettings.saveLastLevel1Screen && screenIdentifier.screen.navigationLevel == 1) {
            dataRepository.localSettings.savedLevel1Screen =
        }
    }

    private fun removeScreensIfNeeded(screenIdentifier: ScreenIdentifier) {
        for (backstackEntry in stateManager.backstack.reversed()) {
            if (
                backstackEntry.screen.navigationLevel < screenIdentifier.screen.navigationLevel ||
                (backstackEntry.screen.navigationLevel == screenIdentifier.screen.navigationLevel && backstackEntry.screen.stackableInstances)
            ) {
                break
            }
            stateManager.removeScreen(backstackEntry)
            screenUIsToForget.add(backstackEntry)
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


class ScreenIdentifier (
    val screen : Screen,
    val params: ScreenParams? = null
) {
    val URI : String
        get() = screen.asString+":"+params.toString()

    // unlike the "params" property, this reified function returns the specific type and not the generic "ScreenParams" interface type
    inline fun <reified T: ScreenParams> params() : T {
        return params as T
    }

    fun getScreenInitSettings(navigation: Navigation) : ScreenInitSettings {
        return screen.initSettings(navigation,this)
    }

}
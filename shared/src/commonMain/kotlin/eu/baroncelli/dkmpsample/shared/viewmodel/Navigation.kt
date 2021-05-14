package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.getScreenInitInfo
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.navigationSettings

class Navigation(val stateManager : StateManager) {

    init {
        val homescreenIdentifier = navigationSettings.homeScreen.screenIdentifier
        navigate(homescreenIdentifier.screen, homescreenIdentifier.params)
    }

    val dataRepository
        get() = stateManager.dataRepository

    val screenUIsToForget = mutableListOf<ScreenIdentifier>()

    // we run each event function on a Dispatchers.Main coroutine
    fun screenCoroutine (block: suspend () -> Unit) {
        stateManager.runInCurrentScreenScope(block)
    }

    val currentScreenIdentifier : ScreenIdentifier
        get() = stateManager.currentScreenIdentifier

    fun navigate(screen: Screen, params: ScreenParams? = null) {
        val screenIdentifier = ScreenIdentifier(screen,params)
        debugLogger.log("navigate to /"+screenIdentifier.URI)
        var shouldTriggerRecomposition = true
        val screenInitInfo = getScreenInitInfo(screenIdentifier)
        if (stateManager.backstack.isNotEmpty()) {
            if (currentScreenIdentifier.screen == screenIdentifier.screen && screenInitInfo.skipDefaultStateRecompositionIfSameAsPreviousScreen) {
                shouldTriggerRecomposition = false
            }
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
        stateManager.addScreen(screenIdentifier, screenInitInfo.defaultState)
        if (shouldTriggerRecomposition) {
            stateManager.triggerRecomposition()
        }
        screenInitInfo.callOnInit(screenIdentifier)
        if (navigationSettings.saveLastLevel1Screen && screenIdentifier.screen.navigationLevel == 1) {
            dataRepository.localSettings.savedLevel1Screen = screenIdentifier.URI
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
            getScreenInitInfo(it).apply {
                if (callOnInitAlsoAfterBackground) {
                    callOnInit(it)
                }
            }
        }
    }

    fun onEnterBackground() {
        debugLogger.log("onEnterBackground: screen scopes are cancelled")
        stateManager.cancelScreenScopes()
    }


}


data class ScreenIdentifier (
    val screen : Screen,
    val params: ScreenParams? = null
) {
    val URI : String
        get() = screen.asString+":"+params.toString()

    // unlike the "params" property, this reified function returns the specific type and not the generic "ScreenParams" interface type
    inline fun <reified T: ScreenParams> params() : T {
        return params as T
    }
}

data class ScreenInitInfo (
    val defaultState : ScreenState,
    val callOnInit: (ScreenIdentifier) -> Unit = {},
    val skipDefaultStateRecompositionIfSameAsPreviousScreen: Boolean = false, // this is useful to avoid showing the "loading..." message, when filtering/displaying local content (which was already downloaded by the first instance of this screen)
    val callOnInitAlsoAfterBackground : Boolean = false,
)
package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.viewmodel.screens.navigationSettings

class Navigation(val stateManager : StateManager) {

    val stateProvider by lazy { StateProvider(stateManager) }
    val events by lazy { Events(stateManager) }

    var savedLevel1URI
        get() = stateManager.dataRepository.localSettings.savedLevel1URI
        set(value) { stateManager.dataRepository.localSettings.savedLevel1URI = value }

    fun getTitle(screenIdentifier: ScreenIdentifier) : String {
        val screenInitSettings = screenIdentifier.getScreenInitSettings(stateManager)
        return screenInitSettings.title
    }

    fun getStartScreenIdentifier() : ScreenIdentifier {
        var startScreenIdentifier = navigationSettings.homeScreen.screenIdentifier
        if (navigationSettings.saveLastLevel1Screen) {
            startScreenIdentifier = ScreenIdentifier.getByURI(savedLevel1URI) ?: startScreenIdentifier
        }
        return startScreenIdentifier
    }

    val only1ScreenInBackstack : Boolean
        get() = stateManager.only1ScreenInBackstack


    // it gets the path when switching to a different level1ScreenIdentifier vertical navigation
    fun getPath(level1ScreenIdentifier: ScreenIdentifier) : MutableList<ScreenIdentifier> {
        val path = mutableListOf<ScreenIdentifier>()
        val verticalNavigationLevel = stateManager.verticalNavigationLevels[level1ScreenIdentifier.URI]
        verticalNavigationLevel?.keys?.sorted()?.forEach {
            if (it>1) {
                path.add( verticalNavigationLevel[it]!! )
            }
        }
        if (navigationSettings.saveLastLevel1Screen) {
            savedLevel1URI = level1ScreenIdentifier.URI
        }
        return path
    }


    fun exitScreen(screenIdentifier: ScreenIdentifier) {
        debugLogger.log("exitScreen: "+screenIdentifier.URI)
        stateManager.removeScreen(screenIdentifier)
    }


    fun onReEnterForeground() {
        // not called at app startup, but only when reentering the app after it was in background
        debugLogger.log("onReEnterForeground: recomposition is triggered")
        val reinitializedScreens = stateManager.reinitScreenScopes()
        stateManager.triggerRecomposition()
        reinitializedScreens.forEach {
            it.getScreenInitSettings(stateManager).apply {
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
package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.viewmodel.screens.navigationSettings

data class NavigationState (
    val level1ScreenIdentifier : ScreenIdentifier,
    var path : MutableList<ScreenIdentifier>, // path is the backstack without the level1ScreenIdentifier
    val nextBackQuitsApp: Boolean
) {
    val topScreenIdentifier = if (path.isEmpty()) level1ScreenIdentifier else path.last()
}


class Navigation(val stateManager : StateManager) {

    val stateProvider by lazy { StateProvider(stateManager) }
    val events by lazy { Events(stateManager) }

    var navigationState = getStartNavigationState()

    val nextBackQuitsApp : Boolean
        get() = stateManager.level1Backstack.size + stateManager.currentVerticalBackstack.size == 2

    var savedLevel1URI
        get() = stateManager.dataRepository.localSettings.savedLevel1URI
        set(value) { stateManager.dataRepository.localSettings.savedLevel1URI = value }


    fun getTitle(screenIdentifier: ScreenIdentifier) : String {
        val screenInitSettings = screenIdentifier.getScreenInitSettings(stateManager)
        return screenInitSettings.title
    }

    fun getStartNavigationState() : NavigationState {
        val screenIdentifier = getStartScreenIdentifier()
        selectLevel1Navigation(screenIdentifier)
        return NavigationState(screenIdentifier, mutableListOf(), nextBackQuitsApp)
    }

    fun getStartScreenIdentifier() : ScreenIdentifier {
        var startScreenIdentifier = navigationSettings.homeScreen.screenIdentifier
        if (navigationSettings.saveLastLevel1Screen) {
            startScreenIdentifier = ScreenIdentifier.getByURI(savedLevel1URI) ?: startScreenIdentifier
        }
        return startScreenIdentifier
    }

    fun updateNavigationState() {
        navigationState = NavigationState(
            level1ScreenIdentifier = stateManager.currentLevel1ScreenIdentifier!!,
            path = getPath(stateManager.currentLevel1ScreenIdentifier!!),
            nextBackQuitsApp = nextBackQuitsApp
        )
        if (navigationSettings.saveLastLevel1Screen && navigationState.topScreenIdentifier.screen.navigationLevel==1) {
            savedLevel1URI = navigationState.topScreenIdentifier.URI
        }
    }

    fun getPath(level1ScreenIdentifier: ScreenIdentifier) : MutableList<ScreenIdentifier> {
        val path = mutableListOf<ScreenIdentifier>()
        val verticalNavigationLevel = stateManager.verticalNavigationLevels[level1ScreenIdentifier.URI]
        verticalNavigationLevel?.keys?.sorted()?.forEach {
            if (it>1) {
                path.add( verticalNavigationLevel[it]!! )
            }
        }
        return path
    }



    // NAVIGATION FUNCTIONS

    fun navigateToScreen(screenIdentifier: ScreenIdentifier) {
        if (isInAnyVerticalNavigationLevel(screenIdentifier)) {
            return
        }
        debugLogger.log("UI NAVIGATION RECOMPOSITION: navigate -> "+screenIdentifier.URI)
        addScreenToBackstack(screenIdentifier)
        updateNavigationState()
    }

    fun selectLevel1Navigation(level1ScreenIdentifier: ScreenIdentifier) {
        debugLogger.log("UI NAVIGATION RECOMPOSITION: navigate level 1 -> "+level1ScreenIdentifier.URI)
        cleanCurrentVerticalBackstacks()
        stateManager.level1Backstack.removeAll { it.URI == level1ScreenIdentifier.URI }
        if (navigationSettings.alwaysQuitOnHomeScreen) {
            if (level1ScreenIdentifier.URI == navigationSettings.homeScreen.screenIdentifier.URI) {
                stateManager.level1Backstack.clear() // remove all elements
            } else if (stateManager.level1Backstack.size == 0) {
                stateManager.level1Backstack.add(navigationSettings.homeScreen.screenIdentifier)
            }
        }
        stateManager.level1Backstack.add(level1ScreenIdentifier)
        if (stateManager.currentVerticalNavigationLevelsMap.size == 0) {
            stateManager.verticalNavigationLevels[level1ScreenIdentifier.URI] = mutableMapOf()
            addScreenToBackstack(level1ScreenIdentifier)
        } else {
            stateManager.currentVerticalNavigationLevelsMap.values.sortedBy { it.screen.navigationLevel }.forEach {
                addScreenToBackstack(it)
            }
        }
        updateNavigationState()
    }

    fun cleanCurrentVerticalBackstacks() {
        // clean current vertical backstack and remove unneeded screens, based on level1VerticalBackstackEnabled()
        if (stateManager.currentVerticalBackstack.size > 0) {
            stateManager.currentVerticalBackstack.filter {
                if (stateManager.currentVerticalBackstack[0].level1VerticalBackstackEnabled()) {
                    it.URI !in stateManager.verticalNavigationLevels[stateManager.currentVerticalBackstack[0].URI]!!.values.map { levelScreen -> levelScreen.URI }
                } else {
                    it.screen.navigationLevel > 1
                }
            }.forEach {
                stateManager.removeScreen(it)
            }
            if (!stateManager.currentVerticalBackstack[0].level1VerticalBackstackEnabled()) {
                stateManager.verticalNavigationLevels[stateManager.currentVerticalBackstack[0].URI]!!.keys.removeAll { it != 1 }
            }
        }
        stateManager.currentVerticalBackstack.clear()
    }


    // ADD SCREEN TO BACKSTACK

    fun addScreenToBackstack(screenIdentifier: ScreenIdentifier) {
        debugLogger.log("addScreenToBackstack: "+screenIdentifier.URI)
        stateManager.currentVerticalBackstack.add(screenIdentifier)
        stateManager.currentVerticalNavigationLevelsMap[screenIdentifier.screen.navigationLevel] = screenIdentifier
        stateManager.initScreen(screenIdentifier)
    }




    // EXIT SCREEN

    fun exitScreen(screenIdentifier: ScreenIdentifier) {
        if (!stateManager.currentVerticalBackstack.any { it.URI == screenIdentifier.URI }) {
            return
        }
        if (screenIdentifier.screen.navigationLevel == 1) {
            stateManager.level1Backstack.removeLast()
            stateManager.verticalNavigationLevels.remove(screenIdentifier.URI)
            stateManager.currentVerticalBackstack.clear()
            stateManager.removeScreen(screenIdentifier)
            stateManager.currentVerticalBackstack.add(stateManager.currentLevel1ScreenIdentifier!!)
            if (!stateManager.isInTheStatesMap(stateManager.currentLevel1ScreenIdentifier!!)) {
                stateManager.verticalNavigationLevels[stateManager.currentLevel1ScreenIdentifier!!.URI] = mutableMapOf(1 to stateManager.currentLevel1ScreenIdentifier!!)
                stateManager.initScreen(stateManager.level1Backstack.last())
            }
        } else {
            stateManager.currentVerticalNavigationLevelsMap.remove(screenIdentifier.screen.navigationLevel)
            stateManager.currentVerticalBackstack.removeAll { it.URI == screenIdentifier.URI }
            stateManager.currentVerticalNavigationLevelsMap[stateManager.currentScreenIdentifier.screen.navigationLevel] = stateManager.currentScreenIdentifier // set new currentScreenIdentifier, after the removal
            if (!isInAnyVerticalNavigationLevel(screenIdentifier)) {
                stateManager.removeScreen(screenIdentifier)
            }
        }
        debugLogger.log("UI NAVIGATION RECOMPOSITION: back to "+stateManager.currentScreenIdentifier.URI)
        val newScreenInitSettings = stateManager.currentScreenIdentifier.getScreenInitSettings(stateManager)
        if (newScreenInitSettings.callOnInitAtEachNavigation) {
            stateManager.runInScreenScope(screenIdentifier) {
                newScreenInitSettings.callOnInit(stateManager)
            }
        }
        updateNavigationState()
    }

    fun isInAnyVerticalNavigationLevel(screenIdentifier: ScreenIdentifier) : Boolean {
        stateManager.verticalNavigationLevels.forEach { verticalNavigation ->
            verticalNavigation.value.forEach {
                if (it.value.URI == screenIdentifier.URI) {
                    return true
                }
            }
        }
        return false
    }




    fun onReEnterForeground() {
        // not called at app startup, but only when reentering the app after it was in background
        debugLogger.log("onReEnterForeground: recomposition is triggered")
        val reinitializedScreens = stateManager.reinitScreenScopes()
        stateManager.triggerAppStateRecomposition()
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
        stateManager.triggerAppStateRecomposition()
    }


}
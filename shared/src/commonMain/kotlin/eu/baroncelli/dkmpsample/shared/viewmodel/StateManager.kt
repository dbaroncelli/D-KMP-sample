package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.navigationSettings
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KClass


interface ScreenState {
    val params : ScreenParams?
}
interface ScreenParams

class UIBackstackEntry (val index : Int, val screenIdentifier : ScreenIdentifier)

class StateManager(repo: Repository) {

    internal val mutableStateFlow = MutableStateFlow(AppState())

    val screenStatesMap : MutableMap<URI,ScreenState> = mutableMapOf() // map of screen states currently in memory
    val screenScopesMap : MutableMap<URI,CoroutineScope> = mutableMapOf() // map of coroutine scopes associated to current screen states

    val verticalBackstacks: MutableMap<URI,MutableList<ScreenIdentifier>> = mutableMapOf() // the map keys are NavigationLevel1 screenIdentifiers
    val level1Backstack: MutableList<ScreenIdentifier> = mutableListOf() // list elements are only NavigationLevel1 screenIdentifiers
    val navigationLevelsMap : MutableMap<Int, ScreenIdentifier> = mutableMapOf() // the map keys are the NavigationLevel numbers

    val lastRemovedScreens = mutableListOf<ScreenIdentifier>()

    internal val dataRepository by lazy { repo }

    val fullNavigationBackstack : List<ScreenIdentifier>
        get() = (currentVerticalBackstack.reversed() + level1Backstack.reversed()).reversed()
    val currentVerticalBackstack: MutableList<ScreenIdentifier>
        get() = verticalBackstacks[navigationLevelsMap[1]?.URI]!!
    val currentScreenIdentifier : ScreenIdentifier
        get() = fullNavigationBackstack.last()
    val only1ScreenInBackstack : Boolean
        get() = fullNavigationBackstack.size == 1


    fun getUIBackstack(): List<UIBackstackEntry> {
        val screenIndentifiersStack: MutableList<ScreenIdentifier> = mutableListOf()
        fullNavigationBackstack.forEach {
            if (screenStatesMap.containsKey(it.URI)) { // omit if it hasn't its state stored
                screenIndentifiersStack.add(it)
            }
        }
        return screenIndentifiersStack.mapIndexed { index, screenIdentifier -> UIBackstackEntry(index, screenIdentifier) }
    }

    fun isInTheStatesMap(screenIdentifier: ScreenIdentifier) : Boolean {
        return screenStatesMap.containsKey(screenIdentifier.URI)
    }

    inline fun <reified T: ScreenState> updateScreen(
            stateClass: KClass<T>,
            update: (T) -> T,
    ) {
        //debugLogger.log("updateScreen: "+currentScreenIdentifier.URI)
        val currentState = screenStatesMap[currentScreenIdentifier.URI] as? T
        if (currentState != null) { // only perform screen state update if screen is currently visible
            screenStatesMap[currentScreenIdentifier.URI] = update(currentState)
            // only trigger recomposition if screen state has changed
            if (!currentState.equals(screenStatesMap[currentScreenIdentifier.URI])) {
                triggerRecomposition()
                debugLogger.log("state changed @ /${currentScreenIdentifier.URI}: recomposition is triggered")
            }
        }
    }

    fun triggerRecomposition() {
        mutableStateFlow.value = AppState(mutableStateFlow.value.recompositionIndex+1)
    }



    // ADD SCREEN FUNCTION

    fun addScreen(screenIdentifier: ScreenIdentifier, screenState: ScreenState) {
        initScreenScope(screenIdentifier)
        screenStatesMap[screenIdentifier.URI] = screenState
        val currentLevel1ScreenIdentifier = navigationLevelsMap[1]
        if (screenIdentifier.screen.navigationLevel == 1) {
            if (currentLevel1ScreenIdentifier != null) {
                val sameAsNewScreen = screenIdentifier.screen == currentLevel1ScreenIdentifier.screen
                clearOldLevel1Screen(currentLevel1ScreenIdentifier, sameAsNewScreen)
            }
            setupNewLevel1Screen(screenIdentifier)
        } else {
            if (currentScreenIdentifier.screen == screenIdentifier.screen && !screenIdentifier.screen.stackableInstances) {
                removeScreenStateAndScope(currentScreenIdentifier)
                currentVerticalBackstack.remove(currentScreenIdentifier)
            }
            if (currentVerticalBackstack.lastOrNull()?.URI != screenIdentifier.URI) {
                currentVerticalBackstack.add(screenIdentifier)
            }
            navigationLevelsMap[screenIdentifier.screen.navigationLevel] = screenIdentifier
        }
        /*
        debugLogger.log("verticalBackstacks: "+verticalBackstacks.keys.toString())
        debugLogger.log("level1Backstack: "+level1Backstack.map{it.URI}.toString())
        debugLogger.log("navigationLevelsMap: "+navigationLevelsMap.keys.toString())
        debugLogger.log("screenScopesMap: "+screenScopesMap.keys.map{it}.toString())
        debugLogger.log("screenStatesMap: "+screenStatesMap.keys.map{it}.toString())
        */
    }



    // REMOVE SCREEN FUNCTIONS

    fun removeLastScreen() {
        removeScreenStateAndScope(currentScreenIdentifier)
        if (currentScreenIdentifier.screen.navigationLevel == 1) {
            level1Backstack.remove(currentScreenIdentifier)
            if (level1Backstack.size == 0) {
                navigationLevelsMap.remove(1)
            } else {
                navigationLevelsMap[1] = currentScreenIdentifier
            }
        } else {
            navigationLevelsMap.remove(currentScreenIdentifier.screen.navigationLevel)
            verticalBackstacks[navigationLevelsMap[1]?.URI]!!.remove(currentScreenIdentifier)
            navigationLevelsMap[currentScreenIdentifier.screen.navigationLevel] = currentScreenIdentifier
        }
    }

    fun removeScreenStateAndScope(screenIdentifier: ScreenIdentifier) {
        debugLogger.log("removed screen /"+screenIdentifier.URI)
        screenScopesMap[screenIdentifier.URI]?.cancel() // cancel screen's coroutine scope
        screenScopesMap.remove(screenIdentifier.URI)
        screenStatesMap.remove(screenIdentifier.URI)
        lastRemovedScreens.add(screenIdentifier)
    }



    // LEVEL 1 NAVIGATION FUNCTIONS

    fun clearOldLevel1Screen(screenIdentifier: ScreenIdentifier, sameAsNewScreen: Boolean) {
        // debugLogger.log("clear vertical backstack /"+screenIdentifier.URI)
        verticalBackstacks[screenIdentifier.URI]?.forEach {
            removeScreenStateAndScope(it)
        }
        if (!screenIdentifier.level1VerticalBackstackEnabled()) {
            verticalBackstacks[screenIdentifier.URI]?.clear()
        }
        if (sameAsNewScreen && !screenIdentifier.screen.stackableInstances) {
            removeScreenStateAndScope(screenIdentifier)
            level1Backstack.remove(screenIdentifier)
        }
    }

    fun setupNewLevel1Screen(screenIdentifier: ScreenIdentifier) {
        level1Backstack.removeAll { it.URI == screenIdentifier.URI }
        if (navigationSettings.alwaysQuitOnHomeScreen) {
            if (screenIdentifier.URI == navigationSettings.homeScreen.screenIdentifier.URI) {
                level1Backstack.clear() // remove all elements
            } else if (level1Backstack.size == 0) {
                level1Backstack.add(navigationSettings.homeScreen.screenIdentifier)
                verticalBackstacks[navigationSettings.homeScreen.screenIdentifier.URI] = mutableListOf()
            }
        }
        level1Backstack.add(screenIdentifier)
        navigationLevelsMap[1] = screenIdentifier
        if (verticalBackstacks[screenIdentifier.URI] != null) {
            verticalBackstacks[screenIdentifier.URI]!!.forEach {
                navigationLevelsMap[it.screen.navigationLevel] = it
            }
        } else {
            verticalBackstacks[screenIdentifier.URI] = mutableListOf()
        }
    }



    // COROUTINE SCOPES FUNCTIONS

    fun initScreenScope(screenIdentifier: ScreenIdentifier) {
        //debugLogger.log("initScreenScope()")
        screenScopesMap[screenIdentifier.URI]?.cancel()
        screenScopesMap[screenIdentifier.URI] = CoroutineScope(Job() + Dispatchers.Main)
    }

    fun reinitScreenScopes() : List<ScreenIdentifier> {
        //debugLogger.log("reinitScreenScopes()")
        navigationLevelsMap.forEach {
            screenScopesMap[it.value.URI] = CoroutineScope(Job() + Dispatchers.Main)
        }
        return navigationLevelsMap.values.toMutableList() // return list of screens whose scope has been reinitialized
    }

    // we run each event function on a Dispatchers.Main coroutine
    fun runInCurrentScreenScope (block: suspend () -> Unit) {
        val screenScope = screenScopesMap[currentScreenIdentifier.URI]
        screenScope?.launch {
            block()
        }
    }

    fun cancelScreenScopes() {
        //debugLogger.log("cancelScreenScopes()")
        screenScopesMap.forEach {
            it.value.cancel() // cancel screen's coroutine scope
        }
    }

}



// APPSTATE DATA CLASS DEFINITION

data class AppState (
    val recompositionIndex : Int = 0,
) {
    fun getNavigation(model: DKMPViewModel) : Navigation {
        return model.navigation
    }
}
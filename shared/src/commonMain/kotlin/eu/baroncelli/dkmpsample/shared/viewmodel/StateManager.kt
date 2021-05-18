package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.ScreenInitSettings
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListType
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.navigationSettings
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass


interface ScreenState {
    val params : ScreenParams?
}
interface ScreenParams

class StateManager(repo: Repository) {

    internal val mutableStateFlow = MutableStateFlow(AppState())

    val screenStatesMap : MutableMap<ScreenIdentifier, ScreenState> = mutableMapOf() // map of screen states currently in memory
    val screenScopesMap : MutableMap<ScreenIdentifier,CoroutineScope> = mutableMapOf() // map of coroutine scopes associated to current screen states

    val verticalBackstacks: MutableMap<ScreenIdentifier,MutableList<ScreenIdentifier>> = mutableMapOf() // the map keys are NavigationLevel1 ScreenIdentifiers
    val level1Backstack: MutableList<ScreenIdentifier> = mutableListOf() // list elements are only NavigationLevel1 ScreenIdentifiers
    val navigationLevelsMap : MutableMap<Int, ScreenIdentifier> = mutableMapOf() // the map keys are the NavigationLevel numbers
    val currentBackstack: MutableList<ScreenIdentifier>
        get() = verticalBackstacks[navigationLevelsMap[1]] ?: mutableListOf()
    val currentScreenIdentifier : ScreenIdentifier
        get() = currentBackstack.lastOrNull() ?: level1Backstack.last()
    val only1ScreenInBackstack : Boolean
        get() = level1Backstack.size == 1 && verticalBackstacks[navigationLevelsMap[1]]?.size == 0

    val lastRemovedScreens = mutableListOf<ScreenIdentifier>()

    internal val dataRepository by lazy { repo }


    inline fun <reified T: ScreenState> updateScreen(
            stateClass: KClass<T>,
            update: (T) -> T,
    ) {
        //debugLogger.log("updateScreen: "+currentScreenIdentifier.URI)
        val currentState = screenStatesMap[currentScreenIdentifier] as? T
        if (currentState != null) { // only perform screen state update if screen is currently visible
            screenStatesMap[currentScreenIdentifier] = update(currentState)
            // only trigger recomposition if screen state has changed
            if (!currentState.equals(screenStatesMap[currentScreenIdentifier])) {
                triggerRecomposition()
                debugLogger.log("state changed @ /${currentScreenIdentifier.URI}: recomposition is triggered")
            }
        }
    }

    fun triggerRecomposition() {
        mutableStateFlow.value = AppState(mutableStateFlow.value.recompositionIndex+1)
    }


    fun initScreenScope(screenIdentifier: ScreenIdentifier) {
        //debugLogger.log("initScreenScope()")
        screenScopesMap[screenIdentifier]?.cancel()
        screenScopesMap[screenIdentifier] = CoroutineScope(Job() + Dispatchers.Main)
    }

    // we run each event function on a Dispatchers.Main coroutine
    fun runInCurrentScreenScope (block: suspend () -> Unit) {
        val screenScope = screenScopesMap[currentScreenIdentifier]
        screenScope?.launch {
            block()
        }
    }


    fun reinitScreenScopes() : List<ScreenIdentifier> {
        //debugLogger.log("reinitScreenScopes()")
        navigationLevelsMap.forEach {
            screenScopesMap[it.value] = CoroutineScope(Job() + Dispatchers.Main)
        }
        return navigationLevelsMap.values.toMutableList() // return list of screens whose scope has been reinitialized
    }

    fun cancelScreenScopes() {
        //debugLogger.log("cancelScreenScopes()")
        screenScopesMap.forEach {
            it.value.cancel() // cancel screen's coroutine scope
        }
    }

    fun addScreen(screenIdentifier: ScreenIdentifier, screenState: ScreenState) {
        initScreenScope(screenIdentifier)
        screenStatesMap[screenIdentifier] = screenState
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
                currentBackstack.remove(currentScreenIdentifier)
            }
            currentBackstack.add(screenIdentifier)
            navigationLevelsMap[screenIdentifier.screen.navigationLevel] = screenIdentifier
        }
        /*
        debugLogger.log("verticalBackstacks: "+verticalBackstacks.keys.map{it.URI}.toString())
        debugLogger.log("level1Backstack: "+level1Backstack.map{it.URI}.toString())
        debugLogger.log("navigationLevelsMap: "+navigationLevelsMap.keys.toString())
        debugLogger.log("screenScopesMap: "+screenScopesMap.keys.map{it.URI}.toString())
        debugLogger.log("screenStatesMap: "+screenStatesMap.keys.map{it.URI}.toString())
        */
    }

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
            verticalBackstacks[navigationLevelsMap[1]]!!.remove(currentScreenIdentifier)
            navigationLevelsMap[currentScreenIdentifier.screen.navigationLevel] = currentScreenIdentifier
        }
    }

    fun removeScreenStateAndScope(screenIdentifier: ScreenIdentifier) {
        debugLogger.log("removed screen /"+screenIdentifier.URI)
        screenScopesMap[screenIdentifier]?.cancel() // cancel screen's coroutine scope
        screenScopesMap.remove(screenIdentifier)
        screenStatesMap.remove(screenIdentifier)
        lastRemovedScreens.add(screenIdentifier)
    }

    fun clearOldLevel1Screen(screenIdentifier: ScreenIdentifier, sameAsNewScreen: Boolean) {
        // debugLogger.log("clear vertical backstack /"+screenIdentifier.URI)
        verticalBackstacks[screenIdentifier]?.forEach {
            removeScreenStateAndScope(it)
        }
        if (!screenIdentifier.level1VerticalBackstackEnabled()) {
            verticalBackstacks[screenIdentifier]?.clear()
        }
        if (sameAsNewScreen && !screenIdentifier.screen.stackableInstances) {
            removeScreenStateAndScope(screenIdentifier)
            level1Backstack.remove(screenIdentifier)
        }
    }


    fun setupNewLevel1Screen(screenIdentifier: ScreenIdentifier) {
        if (navigationSettings.alwaysQuitOnHomeScreen && screenIdentifier == navigationSettings.homeScreen.screenIdentifier) {
            level1Backstack.clear() // remove all elements
        } else {
            level1Backstack.removeAll { it == screenIdentifier } // remove only its element, before adding it to the end
        }
        level1Backstack.add(screenIdentifier)
        navigationLevelsMap[1] = screenIdentifier
        if (verticalBackstacks[screenIdentifier] != null) {
            verticalBackstacks[screenIdentifier]!!.forEach {
                navigationLevelsMap[it.screen.navigationLevel] = it
            }
        } else {
            // debugLogger.log("verticalBackstacks: "+screenIdentifier.URI+" set to mutableListOf()")
            verticalBackstacks[screenIdentifier] = mutableListOf()
        }
    }

}


data class AppState (
    val recompositionIndex : Int = 0,
) {
    fun getNavigation(model: DKMPViewModel) : Navigation {
        return model.navigation
    }
}
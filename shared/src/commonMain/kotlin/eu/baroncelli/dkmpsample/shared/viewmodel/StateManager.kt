package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KClass


interface ScreenState {
    val params : ScreenParams?
}
interface ScreenParams

class StateManager(repo: Repository) {

    internal val mutableStateFlow = MutableStateFlow(AppState())

    val screenStatesMap : MutableMap<ScreenIdentifier, ScreenState> = mutableMapOf() // map of screen states currently in memory
    val screenScopesMap : MutableMap<ScreenIdentifier,CoroutineScope> = mutableMapOf() // map of coroutine scopes associated to current screen states

    val backstack : MutableList<ScreenIdentifier> = mutableListOf()
    val navigationLevelsMap : MutableMap<Int, ScreenIdentifier> = mutableMapOf()
    val currentScreenIdentifier : ScreenIdentifier
        get() = backstack.last()

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

    fun removeLastScreen() {
        removeScreen(backstack.last())
    }

    fun addScreen(screenIdentifier: ScreenIdentifier, screenState: ScreenState) {
        initScreenScope(screenIdentifier)
        screenStatesMap[screenIdentifier] = screenState
        backstack.add(screenIdentifier)
        navigationLevelsMap[screenIdentifier.screen.navigationLevel] = screenIdentifier
    }

    fun removeScreen(screenIdentifier: ScreenIdentifier) {
        debugLogger.log("removed screen /"+screenIdentifier.URI)
        screenScopesMap[screenIdentifier]?.cancel() // cancel screen's coroutine scope
        screenScopesMap.remove(screenIdentifier)
        screenStatesMap.remove(screenIdentifier)
        backstack.remove(screenIdentifier)
        navigationLevelsMap.remove(screenIdentifier.screen.navigationLevel)
    }


}


data class AppState (
    val recompositionIndex : Int = 0,
) {
    fun getNavigation(model: DKMPViewModel) : Navigation {
        return model.navigation
    }
}
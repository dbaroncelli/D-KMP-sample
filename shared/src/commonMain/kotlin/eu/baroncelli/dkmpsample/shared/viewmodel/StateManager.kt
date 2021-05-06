package eu.baroncelli.dkmpsample.shared.viewmodel

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KClass

interface ScreenState // we apply this empty interface to all screen state data classes

class StateManager {

    var currentRouteId = ""

    internal val mutableStateFlow = MutableStateFlow(AppState())

    val screenStatesMap : MutableMap<String,ScreenState> = mutableMapOf() // map of screen states currently in memory
    val screenScopesMap : MutableMap<String,CoroutineScope> = mutableMapOf() // map of coroutine scopes associated to current screen states


    // only called by the State Providers
    inline fun <reified T:ScreenState> getScreen(
        screen: Screen,            // screen enum
        instanceId: String? = null, // instanceId, to be specified for screens that support multiple instances
        initState: () -> T,         // default state on initialization
        callOnInit: () -> Unit,     // event to run on initialization
        callOnInitAlsoAfterBackground : Boolean = false,    // if true, it runs "callOnInit" also after coming back from background
    ) : T {
        var routeId = screen.route
        if (instanceId != null) {
            routeId += "/"+instanceId
        }

        val loggerText = "/"+routeId+": "+T::class.simpleName+" StateProvider is called"
        val currentState = screenStatesMap[routeId] as? T
        if (currentState == null) {
            debugLogger.log(loggerText+" (INITIALIZED state)")
            currentRouteId = routeId
            initScreenScope(currentRouteId)
            val initializedState = initState()
            screenStatesMap[currentRouteId] = initializedState
            callOnInit()
            return initializedState
        }
        if (routeId == currentRouteId && !isScreenScopeActive(routeId)) { // in case it's coming back from background
            debugLogger.log(loggerText+" (reinitialized scope)")
            initScreenScope(routeId)
            if (callOnInitAlsoAfterBackground) {
                callOnInit()
            }
        } else {
            debugLogger.log(loggerText)
        }
        return currentState
    }


    // only called by the State Reducers
    inline fun <reified T:ScreenState> updateScreen(
            stateClass: KClass<T>,
            update: (T) -> T,
    ) {
        //debugLogger.log("updateScreen: "+currentRouteId)
        val currentState = screenStatesMap[currentRouteId] as? T
        if (currentState != null) { // only perform screen state update if screen is currently visible
            screenStatesMap[currentRouteId] = update(currentState)
            // only trigger recomposition if screen state has changed
            if (!currentState.equals(screenStatesMap[currentRouteId])) {
                triggerRecomposition()
                debugLogger.log("/"+currentRouteId+" state changed: recomposition is triggered")
            }
        }
    }

    fun triggerRecomposition() {
        mutableStateFlow.value = AppState(mutableStateFlow.value.recompositionIndex+1)
    }


    fun initScreenScope(routeId: String) {
        //debugLogger.log("initScreenScope()")
        screenScopesMap[routeId]?.cancel()
        screenScopesMap[routeId] = CoroutineScope(Job() + Dispatchers.Main)
    }

    fun getCurrentScreenScope() : CoroutineScope? {
        // debugLogger.log("getCurrentScreenScope(): "+currentRouteId)
        return screenScopesMap[currentRouteId]
    }


    fun isScreenScopeActive(routeId: String) : Boolean {
        return screenScopesMap[routeId]?.isActive == true
    }

    fun cancelScreenScopes() {
        //debugLogger.log("cancelScreenScopes()")
        screenScopesMap.forEach {
            it.value.cancel() // cancel screen's coroutine scope
        }
    }

    fun removeScreen(oldRouteId: String, newRouteId: String) {
        screenScopesMap[oldRouteId]?.cancel() // cancel screen's coroutine scope
        screenScopesMap.remove(oldRouteId)
        screenStatesMap.remove(oldRouteId)
        currentRouteId = newRouteId
    }

}

data class AppState (
    val recompositionIndex : Int = 0
) {
    fun getStateProviders(model: DKMPViewModel) : StateProviders {
        return model.stateProviders
    }
}
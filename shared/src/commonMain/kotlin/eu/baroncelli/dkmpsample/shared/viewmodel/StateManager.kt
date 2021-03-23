package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KClass

class StateManager(repo: Repository = Repository()) {

    internal val mutableStateFlow = MutableStateFlow(AppState())

    var state : AppState
        get() = mutableStateFlow.value
        set (value) { mutableStateFlow.value = value }

    internal val dataRepository by lazy { repo }

    inline fun <reified T:Any> getScreen(theClass : KClass<T>) : T? {
        //debugLogger.log("getScreen: "+T::class.simpleName)
        val screenType = stateToTypeMap[theClass]
        return state.screenStatesMap[screenType] as? T
    }
    inline fun <reified T:Any> setScreen(newScreenState : T) {
        //debugLogger.log("setScreen: "+T::class.simpleName)
        val screenType = stateToTypeMap[T::class]
        val screenStatesMap = state.screenStatesMap.toMutableMap()
        screenStatesMap[screenType!!] = newScreenState
        state = AppState(screenStatesMap = screenStatesMap.toMap())
    }
    inline fun <reified T:Any> updateScreen(theClass: KClass<T>, block: (T) -> T) {
        //debugLogger.log("updateScreen: "+T::class.simpleName)
        val screenType = stateToTypeMap[theClass]
        val screenState = state.screenStatesMap[screenType] as? T
        if (screenState != null) {
            val screenStatesMap = state.screenStatesMap.toMutableMap()
            screenStatesMap[screenType!!] = block(screenState)
            state = AppState(screenStatesMap = screenStatesMap.toMap())
        }
    }

}
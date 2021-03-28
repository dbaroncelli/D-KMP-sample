package eu.baroncelli.dkmpsample.shared.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KClass

class StateManager {

    internal val mutableStateFlow = MutableStateFlow(AppState())

    val screenStatesMap : MutableMap<ScreenType,Any> = mutableMapOf()


    // only called by the State Providers
    inline fun <reified T:Any> getScreen(initState: () -> T, callOnInit: () -> Unit, reinitWhen: (T) -> Boolean = {false}) : T {
        //debugLogger.log("getScreen: "+T::class.simpleName)
        val screenType = stateToTypeMap[T::class]
        val currentState = screenStatesMap[screenType] as? T
        if (currentState == null || reinitWhen(currentState)) {
            val initializedState = initState()
            screenStatesMap[screenType!!] = initializedState // if screenType is null it's because the class hasn't been added to stateToTypeMap
            callOnInit()
            return initializedState
        }
        return currentState
    }


    // only called by the State Reducers
    inline fun <reified T:Any> updateScreen(stateClass: KClass<T>, update: (T) -> T) {
        //debugLogger.log("updateScreen: "+T::class.simpleName)
        val screenType = stateToTypeMap[stateClass]
        val currentState = screenStatesMap[screenType] as? T
        if (currentState != null) { // only perform update if the currentState is in the screenStatesMap
            screenStatesMap[screenType!!] = update(currentState) // if screenType is null it's because the class hasn't been added to stateToTypeMap
            triggerRecomposition()
        }
    }

    fun triggerRecomposition() {
        mutableStateFlow.value = AppState(mutableStateFlow.value.recompositionIndex+1)
    }


}

data class AppState (
    val recompositionIndex : Int = 0
) {
    fun getStateProvider(model : KMPViewModel) : StateProvider {
        return model.stateProvider
    }
}

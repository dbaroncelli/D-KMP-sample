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
            val screenState = initState()
            screenStatesMap[screenType!!] = screenState // if screenType is null it's because the class hasn't been added to stateToTypeMap
            callOnInit()
            return screenState
        }
        return currentState
    }


    // only called by the State Reducers
    inline fun <reified T:Any> updateScreen(stateClass: KClass<T>, block: (T) -> T) {
        //debugLogger.log("updateScreen: "+T::class.simpleName)
        val screenType = stateToTypeMap[stateClass]
        val screenState = screenStatesMap[screenType] as? T
        if (screenState != null) { // only perform update if the screenState is in the screenStatesMap
            screenStatesMap[screenType!!] = block(screenState) // if screenType is null it's because the class hasn't been added to stateToTypeMap
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

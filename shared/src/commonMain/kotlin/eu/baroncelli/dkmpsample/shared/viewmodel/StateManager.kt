package eu.baroncelli.dkmpsample.shared.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KClass

class StateManager {

    internal val mutableStateFlow = MutableStateFlow(AppState())

    val screenStatesMap : MutableMap<ScreenType,Any> = mutableMapOf()

    // only called by the State Providers
    inline fun <reified T:Any> getScreen(theClass : KClass<T>) : T? {
        //debugLogger.log("getScreen: "+T::class.simpleName)
        val screenType = stateToTypeMap[theClass]
        return screenStatesMap[screenType] as? T
    }

    // only called by the State Providers
    inline fun <reified T:Any> initScreen(newScreenState : T) {
        //debugLogger.log("setScreen: "+T::class.simpleName)
        val screenType = stateToTypeMap[T::class]
        if (screenType != null) {
            screenStatesMap[screenType] = newScreenState
        }
    }

    // only called by the State Reducers
    inline fun <reified T:Any> updateScreen(theClass: KClass<T>, block: (T) -> T) {
        //debugLogger.log("updateScreen: "+T::class.simpleName)
        val screenType = stateToTypeMap[theClass]
        val screenState = screenStatesMap[screenType] as? T
        if (screenType != null && screenState != null) { // only perform update if the screenState is in the screenStatesMap
            screenStatesMap[screenType] = block(screenState)
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

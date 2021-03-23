package eu.baroncelli.dkmpsample.shared.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KClass

class StateManager {

    internal val mutableStateFlow = MutableStateFlow(AppState())

    val screenStatesMap : MutableMap<ScreenType,Any> = mutableMapOf()

    inline fun <reified T:Any> getScreen(theClass : KClass<T>) : T? {
        debugLogger.log("getScreen: "+T::class.simpleName)
        val screenType = stateToTypeMap[theClass]
        return screenStatesMap[screenType] as? T
    }
    inline fun <reified T:Any> setScreen(newScreenState : T) : T {
        //debugLogger.log("setScreen: "+T::class.simpleName)
        val screenType = stateToTypeMap[T::class]
        screenStatesMap[screenType!!] = newScreenState
        return newScreenState
    }
    inline fun <reified T:Any> updateScreen(theClass: KClass<T>, block: (T) -> T) {
        //debugLogger.log("updateScreen: "+T::class.simpleName)
        val screenType = stateToTypeMap[theClass]
        val screenState = screenStatesMap[screenType] as? T
        if (screenState != null) {
            screenStatesMap[screenType!!] = block(screenState)
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

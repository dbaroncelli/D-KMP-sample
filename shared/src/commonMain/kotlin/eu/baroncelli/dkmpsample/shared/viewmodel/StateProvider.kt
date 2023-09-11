package eu.baroncelli.dkmpsample.shared.viewmodel

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StateProvider(val stateManager: StateManager) {

    inline fun <reified T : ScreenState> get(screenIdentifier: ScreenIdentifier): StateFlow<T> {
        return getScreenState(screenIdentifier)
    }

    // reified functions cannot be exported to iOS, so we use this function returning the "ScreenState" interface type
    // on Swift, we then need to cast it to the specific state class
    fun getToCast(screenIdentifier: ScreenIdentifier): StateFlow<ScreenState> {
        return getScreenState(screenIdentifier)
    }

    inline fun <reified T : ScreenState> getScreenState(screenIdentifier: ScreenIdentifier): StateFlow<T> {
        //debugLogger.log("getScreenState: "+screenIdentifier.URI)
        return stateManager.screenStatesMap[screenIdentifier.URI]!!.asStateFlow() as StateFlow<T>
    }

}




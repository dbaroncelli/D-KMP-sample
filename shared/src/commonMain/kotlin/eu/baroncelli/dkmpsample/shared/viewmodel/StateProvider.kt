package eu.baroncelli.dkmpsample.shared.viewmodel

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StateProvider (val stateManager : StateManager) {

    fun getScreenStateFlow(screenIdentifier: ScreenIdentifier) : StateFlow<ScreenState> {
        //debugLogger.log("getScreenStateFlow: "+screenIdentifier.URI)
        return stateManager.screenStatesMap[screenIdentifier.URI]!!.asStateFlow()
    }

}




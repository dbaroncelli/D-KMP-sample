package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.viewmodel.appstate.AppState
import eu.baroncelli.dkmpsample.shared.viewmodel.appstate.detail.DetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.appstate.master.MasterState
import kotlinx.coroutines.flow.MutableStateFlow

class StateManager(repo: Repository = Repository()) {

    internal val mutableStateFlow = MutableStateFlow(AppState())
    internal var state : AppState
        get() = mutableStateFlow.value
        set (value) { mutableStateFlow.value = value }
    internal val dataRepository = repo

    // define a function to update each substate
    fun updateMasterState(block: (MasterState) -> MasterState) {
        //debugLogger.d {"changed master state"}
        state = state.copy(masterState = block(state.masterState))
    }
    fun updateDetailState(block: (DetailState) -> DetailState) {
        //debugLogger.d {"changed detail state"}
        state = state.copy(detailState = block(state.detailState))
    }

}
package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.viewmodel.detail.DetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.master.MasterState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StateManager(repo: Repository = Repository()) {

    internal val mutableStateFlow = MutableStateFlow(AppState())
    internal var state : AppState
        get() = mutableStateFlow.value
        set (value) { mutableStateFlow.value = value }
    internal val dataRepository = repo

}
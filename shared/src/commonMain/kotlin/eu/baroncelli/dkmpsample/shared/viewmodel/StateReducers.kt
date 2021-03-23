package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.datalayer.Repository

class StateReducers (stateManager : StateManager, repo: Repository = Repository()) {

    val stateManager by lazy { stateManager }

    internal val dataRepository by lazy { repo }

}
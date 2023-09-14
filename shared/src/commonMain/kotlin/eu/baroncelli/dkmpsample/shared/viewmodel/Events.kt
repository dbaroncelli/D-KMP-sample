package eu.baroncelli.dkmpsample.shared.viewmodel


class Events(val stateManager: StateManager) {

    val dataRepository
        get() = stateManager.dataRepository

    // we run each event function on a Dispatchers.Main coroutine
    fun screenCoroutine(block: suspend () -> Unit) {
        debugLogger.log("/" + stateManager.currentScreenIdentifier.URI + ": an Event is called")
        stateManager.runInScreenScope { block() }
    }

}
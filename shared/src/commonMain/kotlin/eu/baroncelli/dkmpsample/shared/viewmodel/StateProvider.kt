package eu.baroncelli.dkmpsample.shared.viewmodel


class StateProvider (model : KMPViewModel) {
    val stateManager = model.stateManager
    val events = model.events
    fun getState() : AppState {
        return stateManager.state
    }
}
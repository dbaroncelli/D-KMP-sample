package eu.baroncelli.dkmpsample.shared.viewmodel


class StateProvider (model : KMPViewModel) {
    private val stateManager = model.stateManager
    internal val events = model.events
    fun getState() : AppState {
        return stateManager.state
    }
}
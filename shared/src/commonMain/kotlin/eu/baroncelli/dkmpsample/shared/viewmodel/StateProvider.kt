package eu.baroncelli.dkmpsample.shared.viewmodel


class StateProvider (sm : StateManager, ev : Events) {
    val stateManager = sm
    internal val events = ev
}
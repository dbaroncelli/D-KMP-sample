package eu.baroncelli.dkmpsample.shared.viewmodel


class StateProvider (stateManager : StateManager, events : Events) {
    internal val stateManager by lazy { stateManager }
    internal val events by lazy { events }
}
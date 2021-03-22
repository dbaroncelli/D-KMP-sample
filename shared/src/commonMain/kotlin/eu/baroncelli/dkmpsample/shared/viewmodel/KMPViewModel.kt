package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.DebugLogger
import kotlinx.coroutines.flow.StateFlow

val debugLogger by lazy { DebugLogger("D-KMP-SAMPLE") }

class KMPViewModel {

    val stateFlow: StateFlow<AppState>
        get() = stateManager.mutableStateFlow

    internal val stateManager = StateManager()

    val events = Events(stateManager)

    val stateProvider = StateProvider(stateManager, events)

}

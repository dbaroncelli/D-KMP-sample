package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.DebugLogger
import kotlinx.coroutines.flow.StateFlow

val debugLogger by lazy { DebugLogger("D-KMP-SAMPLE") }

class KMPViewModel {

    val stateFlow: StateFlow<AppState>
        get() = stateManager.mutableStateFlow

    internal val stateManager by lazy { StateManager() }

    val events by lazy { Events(stateManager) }

    internal val stateProvider by lazy { StateProvider(stateManager, events) }

}

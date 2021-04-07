package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.DebugLogger
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import kotlinx.coroutines.flow.StateFlow

val debugLogger by lazy { DebugLogger("D-KMP SAMPLE") }

class KMPViewModel (repo: Repository) {

    companion object Factory {
        // factory methods are defined in the platform-specific shared code
    }

    val stateFlow: StateFlow<AppState>
        get() = stateManager.mutableStateFlow

    private val stateManager by lazy { StateManager() }

    private val stateReducers by lazy { StateReducers(stateManager, repo) }

    val events by lazy { Events(stateReducers) }

    internal val stateProvider by lazy { StateProvider(stateManager, events) }

}
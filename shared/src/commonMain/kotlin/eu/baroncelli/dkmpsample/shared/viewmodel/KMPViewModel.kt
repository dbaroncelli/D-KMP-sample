package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.DebugLogger
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import kotlinx.coroutines.flow.StateFlow

val debugLogger by lazy { DebugLogger("D-KMP SAMPLE") }

class KMPViewModel (repo: Repository) {

    companion object Factory {
        // factory methods are defined in the platform-specific shared code (androidMain and iosMain)
    }

    val stateFlow: StateFlow<AppState>
        get() = stateManager.mutableStateFlow

    private val stateManager by lazy { StateManager() }
    private val stateReducers by lazy { StateReducers(stateManager, repo) }
    val events by lazy { Events(stateReducers) }
    internal val stateProviders by lazy { StateProviders(stateManager, events) }

    fun onReEnterForeground() {
        // not called at startup, but only when reentering the app after it was in background
        debugLogger.log("onReEnterForeground")
        stateManager.triggerRecomposition()
    }

    fun onEnterBackground() {
        debugLogger.log("onEnterBackground")
        stateManager.cancelScreenScopes()
    }

}
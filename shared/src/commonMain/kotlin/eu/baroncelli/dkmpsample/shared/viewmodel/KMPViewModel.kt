package eu.baroncelli.dkmpsample.shared.viewmodel

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import kotlinx.coroutines.flow.StateFlow

val debugLogger by lazy { Kermit(CommonLogger()).withTag("D-KMP-SAMPLE") }

class KMPViewModel {

    val stateFlow: StateFlow<AppState>
        get() = stateManager.mutableStateFlow

    internal val stateManager = StateManager()

    val events = Events(stateManager)

}

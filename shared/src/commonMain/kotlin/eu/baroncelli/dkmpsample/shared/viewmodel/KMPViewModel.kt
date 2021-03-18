package eu.baroncelli.dkmpsample.shared.viewmodel

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import eu.baroncelli.dkmpsample.shared.viewmodel.appstate.AppState
import eu.baroncelli.dkmpsample.shared.viewmodel.events.appStarted
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow

val debugLogger by lazy { Kermit(CommonLogger()).withTag("D-KMP-SAMPLE") }

class KMPViewModel {

    val stateFlow: StateFlow<AppState>
        get() = stateManager.mutableStateFlow

    internal val stateManager = StateManager()

    // this block function is to run suspend functions in our events:
    // we always launch the coroutine on the main thread, because the DataLayer suspend functions
    // can always decide to run their code in a background thread by using "withContext()"
    // (e.g. that's what Ktor Http Client does, under the hood)
    fun launchCoroutine (block: suspend () -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            block()
        }
    }


    // call appStarted() at startup
    init { appStarted() }

}

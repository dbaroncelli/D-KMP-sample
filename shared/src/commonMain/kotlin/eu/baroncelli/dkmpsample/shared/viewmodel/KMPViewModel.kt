package eu.baroncelli.dkmpsample.shared.viewmodel

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import eu.baroncelli.dkmpsample.shared.viewmodel.appstate.AppState
import eu.baroncelli.dkmpsample.shared.viewmodel.events.appStarted
import eu.baroncelli.dkmpsample.shared.viewmodel.styling.Themes
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

val debugLogger by lazy { Kermit(CommonLogger()).withTag("D-KMP-SAMPLE") }

class KMPViewModel {

    val stateFlow: StateFlow<AppState>
        get() = stateManager.mutableStateFlow

    internal val stateManager = StateManager()

    val themes = Themes

    // this function is to run asynchronous code in our events:
    // we always launch the coroutine on the main thread, because the DataLayer suspend functions
    // can always decide to run their code in a background thread by using "withContext()"
    // (e.g. that's what Ktor Http Client does, under the hood)
    fun launchCoroutine (block: suspend () -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            block()
        }
    }


    // call appStarted, at startup
    init { appStarted() }

}

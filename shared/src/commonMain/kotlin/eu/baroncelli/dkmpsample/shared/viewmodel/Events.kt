package eu.baroncelli.dkmpsample.shared.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Events (val stateManager : StateManager) {

    // this block function is to run suspend functions in our events:
    // we always launch the coroutine on the main thread, because the DataLayer suspend functions
    // can always decide to run their code in a background thread by using "withContext()"
    // (e.g. that's what Ktor Http Client does, under the hood)
    fun launchCoroutine (block: suspend () -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            block()
        }
    }

}
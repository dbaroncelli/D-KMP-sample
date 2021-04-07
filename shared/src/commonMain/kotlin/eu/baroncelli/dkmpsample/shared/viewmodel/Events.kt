package eu.baroncelli.dkmpsample.shared.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Events (stateReducers: StateReducers) {

    internal val stateReducers by lazy { stateReducers }

    // we run each event function on a main thread coroutine
    fun onMainCoroutine (block: suspend () -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            block()
        }
    }

}
package eu.baroncelli.dkmpsample.shared.viewmodel

import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class Events (stateReducers: StateReducers) {

    internal val stateReducers by lazy { stateReducers }

    // we run each event function on a Dispatchers.Main coroutine
    fun screenCoroutine (block: suspend () -> Unit) {
        val screenScope = stateReducers.stateManager.getCurrentScreenScope()
        debugLogger.log("/"+stateReducers.stateManager.currentRouteId+": an Event is called")
        screenScope?.launch {
            block()
        }
    }

}
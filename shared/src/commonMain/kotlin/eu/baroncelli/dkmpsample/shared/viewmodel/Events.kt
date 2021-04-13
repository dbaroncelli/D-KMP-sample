package eu.baroncelli.dkmpsample.shared.viewmodel

import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class Events (stateReducers: StateReducers) {

    internal val stateReducers by lazy { stateReducers }

    // we run each event function on a Dispatchers.Main coroutine, scoped on the specific screen
    fun screenCoroutine (stateClass : KClass<out ScreenState>, block: suspend () -> Unit) {
        val screenScope = stateReducers.stateManager.getScreenScope(stateClass)
        screenScope?.launch {
            block()
        }
    }

}
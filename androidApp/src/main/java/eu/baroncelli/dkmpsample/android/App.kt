package eu.baroncelli.dkmpsample.android

import android.app.Application
import androidx.lifecycle.*
import eu.baroncelli.dkmpsample.shared.viewmodel.DKMPViewModel
import eu.baroncelli.dkmpsample.shared.viewmodel.getAndroidInstance

class DKMPApp : Application() {

    lateinit var model: DKMPViewModel

    override fun onCreate() {
        super.onCreate()
        model = DKMPViewModel.Factory.getAndroidInstance(this)
        
        val appLifecycleObserver = AppLifecycleObserver(model)
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
    }

}

class AppLifecycleObserver (private val model: DKMPViewModel) : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START ->
                if (model.stateFlow.value.recompositionIndex > 0) { // not calling at app startup
                    model.navigation.onReEnterForeground()
                }
            Lifecycle.Event.ON_STOP ->
                model.navigation.onEnterBackground()
            else ->
                return
        }
    }

}
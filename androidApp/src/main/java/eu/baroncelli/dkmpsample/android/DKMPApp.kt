package eu.baroncelli.dkmpsample.android

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import eu.baroncelli.dkmpsample.shared.viewmodel.DKMPViewModel
import eu.baroncelli.dkmpsample.shared.viewmodel.getAndroidInstance

class DKMPApp : Application() {

    lateinit var model: DKMPViewModel

    override fun onCreate() {
        super.onCreate()
        model = DKMPViewModel.Factory.getAndroidInstance(this)
        
        val appLifecycleObserver = AppLifecycleObserver(model)
        ProcessLifecycleOwner.get().getLifecycle().addObserver(appLifecycleObserver)
    }

}

class AppLifecycleObserver (val model: DKMPViewModel) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
        if (model.stateFlow.value.recompositionIndex > 0) { // not calling at app startup
            model.onReEnterForeground()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        model.onEnterBackground()
    }

}
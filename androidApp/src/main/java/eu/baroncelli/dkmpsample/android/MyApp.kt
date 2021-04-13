package eu.baroncelli.dkmpsample.android

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import eu.baroncelli.dkmpsample.shared.viewmodel.KMPViewModel
import eu.baroncelli.dkmpsample.shared.viewmodel.getAndroidInstance

class MyApp : Application() {

    lateinit var kmpViewModel: KMPViewModel

    override fun onCreate() {
        super.onCreate()
        kmpViewModel = KMPViewModel.Factory.getAndroidInstance(this)

        val appLifecycleObserver = AppLifecycleObserver(kmpViewModel)
        ProcessLifecycleOwner.get().getLifecycle().addObserver(appLifecycleObserver)
    }

}

class AppLifecycleObserver (val model: KMPViewModel) : LifecycleObserver {

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
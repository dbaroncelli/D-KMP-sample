package eu.baroncelli.dkmpsample.android

import android.app.Application
import eu.baroncelli.dkmpsample.shared.viewmodel.KMPViewModel
import eu.baroncelli.dkmpsample.shared.viewmodel.getAndroidInstance

class MyApp : Application() {

    lateinit var kmpViewModel: KMPViewModel

    override fun onCreate() {
        super.onCreate()
        kmpViewModel = KMPViewModel.Factory.getAndroidInstance(this)
    }
}

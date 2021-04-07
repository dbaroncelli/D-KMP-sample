package eu.baroncelli.dkmpsample.android

import android.content.Context
import androidx.lifecycle.ViewModel
import eu.baroncelli.dkmpsample.shared.viewmodel.KMPViewModel
import eu.baroncelli.dkmpsample.shared.viewmodel.getAndroidInstance


class AppViewModel : ViewModel() {

    private lateinit var coreModel : KMPViewModel

    fun getCoreViewModel(context : Context) : KMPViewModel {
        if (!this::coreModel.isInitialized) {
            coreModel = KMPViewModel.Factory.getAndroidInstance(context)
        }
        return coreModel
    }
}
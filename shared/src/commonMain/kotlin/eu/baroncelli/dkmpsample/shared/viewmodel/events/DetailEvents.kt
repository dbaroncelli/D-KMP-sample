package eu.baroncelli.dkmpsample.shared.viewmodel.events

import eu.baroncelli.dkmpsample.shared.viewmodel.KMPViewModel
import eu.baroncelli.dkmpsample.shared.viewmodel.statereducers.getDetails
import eu.baroncelli.dkmpsample.shared.viewmodel.statereducers.setDetailLoading


fun KMPViewModel.loadDetailItem(country: String) {
    stateManager.setDetailLoading()
    launchCoroutine {
        stateManager.getDetails(country)
    }
}
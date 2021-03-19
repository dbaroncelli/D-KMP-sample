package eu.baroncelli.dkmpsample.shared.viewmodel.detail

import eu.baroncelli.dkmpsample.shared.viewmodel.Events

/************ INTERNAL EVENT FUNCTION USED BY THE STATE PROVIDER ***************/

internal fun Events.updateDetailData(country: String) {
    stateManager.setDetailLoading(country)
    launchCoroutine {
        stateManager.getDetails(country)
    }
}
package eu.baroncelli.dkmpsample.shared.viewmodel.detail

import eu.baroncelli.dkmpsample.shared.viewmodel.Events

/********** INTERNAL EVENT FUNCTION, USED BY THE STATE PROVIDER **********/

internal fun Events.loadDetailData(country: String) {
    stateManager.setDetailLoading(country)
    // launch a coroutine, as "setDetailData" is a suspend function
    launchCoroutine {
        stateManager.setDetailData(country)
    }
}
package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail

import eu.baroncelli.dkmpsample.shared.viewmodel.Events

/********** INTERNAL EVENT FUNCTION, USED BY THE STATE PROVIDER **********/

internal fun Events.loadCountryDetailData(country: String) {
    // launch a coroutine, as "updateCountryDetail" is a suspend function
    launchCoroutine {
        stateReducers.updateCountryDetail(country)
    }
}
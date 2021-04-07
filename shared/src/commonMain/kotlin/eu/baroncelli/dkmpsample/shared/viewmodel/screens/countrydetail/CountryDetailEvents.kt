package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail

import eu.baroncelli.dkmpsample.shared.viewmodel.Events

/********** INTERNAL event function, used by the StateProvider **********/

internal fun Events.loadCountryDetailData(country: String) = onMainCoroutine {
    stateReducers.updateCountryDetail(country)
}
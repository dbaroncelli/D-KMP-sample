package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail

import eu.baroncelli.dkmpsample.shared.viewmodel.Screen
import eu.baroncelli.dkmpsample.shared.viewmodel.StateProviders


fun StateProviders.getCountryDetailState(country : String) : CountryDetailState {
    // the state gets initialized with "initState":
    //      WHEN this function is called for the first time
    //          OR if the "reinitWhen" condition is true
    // after initialization, the "callOnInit" code gets called
    return stateManager.getScreen(
        screen = Screen.CountryDetail,
        instanceId = country,
        initState = { CountryDetailState(countryName = country, isLoading = true) },
        callOnInit = { events.loadCountryDetailData(country) },
    )
}
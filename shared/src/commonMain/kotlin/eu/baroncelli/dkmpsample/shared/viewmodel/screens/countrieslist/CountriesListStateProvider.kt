package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProviders


fun StateProviders.getCountriesListState() : CountriesListState {

    // the state gets initialized with "initState":
    //      ONLY WHEN this function is called for the first time
    // after initialization, the "callOnInit" code gets called
    return stateManager.getScreen(
        initState = { CountriesListState(isLoading = true) },
        callOnInit = { events.loadCountriesListData() }
    )

}
package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider


fun StateProvider.getCountriesListState() : CountriesListState {

    // the state gets initialized only the first time this function gets called
    // the code in "callOnInit" is called on initialization
    return stateManager.getScreen(
        initState = { CountriesListState(isLoading = true) },
        callOnInit = { events.loadCountriesListData() }
    )

}
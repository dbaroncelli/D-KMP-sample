package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider


fun StateProvider.getCountriesListState() : CountriesListState {

    // the screen gets initialized only the first time it gets called
    return stateManager.getScreen(
        initState = { CountriesListState(isLoading = true) },
        callOnInit = { events.loadCountriesListData() }
    )

}
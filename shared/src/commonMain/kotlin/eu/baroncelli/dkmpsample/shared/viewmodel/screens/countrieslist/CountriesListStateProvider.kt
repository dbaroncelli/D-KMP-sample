package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider


fun StateProvider.getCountriesListState() : CountriesListState {

    return stateManager.getScreen(
        initState = { CountriesListState(isLoading = true) },
        callOnInit = { events.loadCountriesListData() }
    )

}
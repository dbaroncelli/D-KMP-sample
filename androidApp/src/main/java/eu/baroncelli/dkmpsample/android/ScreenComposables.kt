package eu.baroncelli.dkmpsample.android

import eu.baroncelli.dkmpsample.android.screens.countrydetail.CountryDetailScreen
import eu.baroncelli.dkmpsample.android.screens.countrieslist.CountriesListScreen
import eu.baroncelli.dkmpsample.shared.viewmodel.Events
import eu.baroncelli.dkmpsample.shared.viewmodel.Screen
import eu.baroncelli.dkmpsample.shared.viewmodel.StateProviders
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.getCountryDetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.getCountriesListState


fun DKMPNavigation.getComposables(stateProviders: StateProviders, events: Events) {

    dkmpComposable(Screen.CountriesList) {
        CountriesListScreen(
            countriesListState = stateProviders.getCountriesListState(),
            events = events,
            onListItemClick = { navigate(Screen.CountryDetail, it) },
        )
    }

    dkmpComposable(Screen.CountryDetail, true) {
        val countryName = getScreenInstanceId()!!
        CountryDetailScreen(
            countryDetailState = stateProviders.getCountryDetailState(countryName)
        )
    }

}
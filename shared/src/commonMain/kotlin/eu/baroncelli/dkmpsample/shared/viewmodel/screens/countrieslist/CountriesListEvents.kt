package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.viewmodel.Events

/********** INTERNAL event function, used by the StateProvider **********/

internal fun Events.loadCountriesListData() = screenCoroutine(CountriesListState::class) {
    stateReducers.updateCountriesList(null)
}


/********** PUBLIC event functions **********/

fun Events.selectMenuItem(menuItem: MenuItem) = screenCoroutine(CountriesListState::class) {
    stateReducers.updateCountriesList(menuItem)
}

fun Events.selectFavorite(country: String) = screenCoroutine(CountriesListState::class) {
    stateReducers.toggleFavorite(country)
}
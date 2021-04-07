package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.viewmodel.Events

/********** INTERNAL event function, used by the StateProvider **********/

internal fun Events.loadCountriesListData() = onMainCoroutine {
    stateReducers.updateCountriesList(null)
}


/********** PUBLIC event functions **********/

fun Events.selectMenuItem(menuItem: MenuItem) = onMainCoroutine {
    stateReducers.updateCountriesList(menuItem)
}

fun Events.selectFavorite(country: String) = onMainCoroutine {
    stateReducers.toggleFavorite(country)
}
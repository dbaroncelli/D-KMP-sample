package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.viewmodel.Events

/********** INTERNAL event function, used by the StateProvider **********/

internal fun Events.loadCountriesListData() {
    val restoredSelectedMenuItem = stateReducers.restoreSelectedMenuItem()
    //debugLogger.log("restoredSelectedMenuItem: "+restoredSelectedMenuItem)
    // launch a coroutine, as "updateCountriesList" is a suspend function
    launchCoroutine {
        stateReducers.updateCountriesList(restoredSelectedMenuItem)
    }
}


/********** PUBLIC event functions **********/

fun Events.selectMenuItem(menuItem: MenuItem) {
    // launch a coroutine, as "updateCountriesList" is a suspend function
    launchCoroutine {
        stateReducers.updateCountriesList(menuItem)
    }
}

fun Events.selectFavorite(country: String) {
    stateReducers.toggleFavorite(country)
}
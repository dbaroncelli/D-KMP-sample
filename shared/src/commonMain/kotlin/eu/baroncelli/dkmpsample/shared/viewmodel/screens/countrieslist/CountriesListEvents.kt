package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.viewmodel.Events

/********** INTERNAL EVENT FUNCTION, USED BY THE STATE PROVIDER **********/

internal fun Events.loadCountriesListData() {
    val restoredSelectedMenuItem = stateReducers.restoreSelectedMenuItem()
    //debugLogger.log("restoredSelectedMenuItem: "+restoredSelectedMenuItem)
    // launch a coroutine, as "updateCountriesList" is a suspend function
    launchCoroutine {
        stateReducers.updateCountriesList(restoredSelectedMenuItem)
    }
}


/********** PUBLIC EVENT FUNCTIONS **********/

fun Events.selectMenuItem(menuItem: MenuItem) {
    // launch a coroutine, as "updateCountriesList" is a suspend function
    launchCoroutine {
        stateReducers.updateCountriesList(menuItem)
    }
}

fun Events.selectFavorite(country: String) {
    stateReducers.toggleFavorite(country)
}
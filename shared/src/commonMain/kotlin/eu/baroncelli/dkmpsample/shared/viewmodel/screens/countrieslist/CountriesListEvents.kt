package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.viewmodel.Events

/********** INTERNAL EVENT FUNCTION, USED BY THE STATE PROVIDER **********/

internal fun Events.loadCountriesListData() {
    val restoredSelectedMenuItem = stateManager.restoreSelectedMenuItem()
    //debugLogger.log("restoredSelectedMenuItem: "+restoredSelectedMenuItem)
    // launch a coroutine, as "updateCountriesList" is a suspend function
    launchCoroutine {
        stateManager.updateCountriesList(restoredSelectedMenuItem)
    }
}


/********** PUBLIC EVENT FUNCTIONS **********/

fun Events.selectMenuItem(menuItem: MenuItem) {
    // launch a coroutine, as "updateCountriesList" is a suspend function
    launchCoroutine {
        stateManager.updateCountriesList(menuItem)
    }
}

fun Events.selectFavorite(country: String) {
    stateManager.toggleFavorite(country)
}
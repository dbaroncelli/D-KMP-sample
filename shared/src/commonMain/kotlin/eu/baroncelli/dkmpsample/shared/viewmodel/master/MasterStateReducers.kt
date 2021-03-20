package eu.baroncelli.dkmpsample.shared.viewmodel.master

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getCountriesListData
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger

/********** LAMBDA FUNCTION, USED BY THE STATE REDUCERS **********/

fun StateManager.updateMasterState(block: (MasterState) -> MasterState) {
    //debugLogger.d {"changed master state"}
    state = state.copy(masterState = block(state.masterState))
}



/********** STATE REDUCERS **********/

fun StateManager.restoreSelectedMenuItem() : MenuItem {
    // restore the selected MenuItem saved in Settings into the state object
    val savedSelectedMenuItem = dataRepository.localSettings.selectedMenuItem
    updateMasterState {
        it.copy(selectedMenuItem = savedSelectedMenuItem)
    }
    return savedSelectedMenuItem
}


suspend fun StateManager.setMasterDataByMenuItem(menuItem: MenuItem) {
    // set master data into the state object, after retrieving the countries list from the repository
    var listData = dataRepository.getCountriesListData()
    if (menuItem == MenuItem.FAVORITES) {
        // in case the Favorites tab has been selected, only get the favorite countries
        listData = listData.filter { dataRepository.localSettings.favoriteCountries.containsKey(it.name) }
    }
    updateMasterState {
        it.copy(
            countriesList = listData,
            selectedMenuItem = menuItem,
            isLoading = false,
            favoriteCountries = dataRepository.localSettings.favoriteCountries,
        )
    }
    // save the MenuItem again into the Settings (as a "side-effect")
    dataRepository.localSettings.selectedMenuItem = menuItem
}


fun StateManager.toggleFavorite(country: String) {
    // update the favorites maps and save it into the state object
    val favoriteCountries = dataRepository.localSettings.favoriteCountries
    if (favoriteCountries.containsKey(country)) {
        favoriteCountries.remove(country)
    } else {
        favoriteCountries[country] = true
    }
    updateMasterState {
        it.copy(favoriteCountries = favoriteCountries)
    }
    // save the favoriteCountries map again into the Settings (as a "side-effect")
    dataRepository.localSettings.favoriteCountries = favoriteCountries
}
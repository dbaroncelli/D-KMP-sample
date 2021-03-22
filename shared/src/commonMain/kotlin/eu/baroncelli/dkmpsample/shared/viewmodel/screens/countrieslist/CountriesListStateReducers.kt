package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getCountriesListData
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager


fun StateManager.restoreSelectedMenuItem() : MenuItem {
    //debugLogger.log("START restoreSelectedMenuItem")
    // restore the selected MenuItem saved in Settings into the state object
    val savedSelectedMenuItem = dataRepository.localSettings.selectedMenuItem
    updateScreen(CountriesListState::class) {
        it.copy(selectedMenuItem = savedSelectedMenuItem)
    }
    return savedSelectedMenuItem
}


suspend fun StateManager.updateCountriesList(menuItem: MenuItem) {
    // update CountriesListState, after retrieving the countries list from the repository
    var listData = dataRepository.getCountriesListData()
    if (menuItem == MenuItem.FAVORITES) {
        // in case the Favorites tab has been selected, only get the favorite countries
        listData = listData.filter { dataRepository.localSettings.favoriteCountries.containsKey(it.name) }
    }
    updateScreen(CountriesListState::class) {
        it.copy(
            countriesListItems = listData,
            selectedMenuItem = menuItem,
            isLoading = false,
            favoriteCountries = dataRepository.localSettings.favoriteCountries,
        )
    }
    // save the MenuItem again into the Settings (as a "side-effect")
    dataRepository.localSettings.selectedMenuItem = menuItem
}


fun StateManager.toggleFavorite(country: String) {
    // update the favorites map and save it into the state object
    val favoriteCountries = dataRepository.localSettings.favoriteCountries
    if (favoriteCountries.containsKey(country)) {
        favoriteCountries.remove(country)
    } else {
        favoriteCountries[country] = true
    }
    updateScreen(CountriesListState::class) {
        it.copy(favoriteCountries = favoriteCountries)
    }
    // save the favoriteCountries map again into the Settings (as a "side-effect")
    dataRepository.localSettings.favoriteCountries = favoriteCountries
}
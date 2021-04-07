package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getCountriesListData
import eu.baroncelli.dkmpsample.shared.datalayer.functions.getFavoriteCountriesMap
import eu.baroncelli.dkmpsample.shared.viewmodel.StateReducers


suspend fun StateReducers.updateCountriesList(selectedMenuItem: MenuItem?) {
    val menuItem = selectedMenuItem ?: dataRepository.localSettings.selectedMenuItem
    var listData = dataRepository.getCountriesListData()
    val favorites = dataRepository.getFavoriteCountriesMap()
    if (menuItem == MenuItem.FAVORITES) {
        // in case the Favorites tab is selected, only get the favorite countries
        listData = listData.filter { favorites.containsKey(it.name) }
    }
    // update CountriesListState, after retrieving data from the repository
    stateManager.updateScreen(CountriesListState::class) {
        it.copy(
            countriesListItems = listData,
            selectedMenuItem = menuItem,
            isLoading = false,
            favoriteCountries = favorites,
        )
    }
    // save the MenuItem again into the Settings (as a "side-effect")
    dataRepository.localSettings.selectedMenuItem = menuItem
}


suspend fun StateReducers.toggleFavorite(country: String) {
    val favorites = dataRepository.getFavoriteCountriesMap(toggleCountry = country)
    // update CountriesListState with new favorites map, after toggling the value for the specified country
    stateManager.updateScreen(CountriesListState::class) {
        it.copy(favoriteCountries = favorites)
    }
}
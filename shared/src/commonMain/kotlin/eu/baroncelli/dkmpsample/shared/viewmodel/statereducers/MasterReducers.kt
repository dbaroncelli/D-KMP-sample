package eu.baroncelli.dkmpsample.shared.viewmodel.statereducers

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getCountriesListData
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager
import eu.baroncelli.dkmpsample.shared.viewmodel.appstate.master.MenuItem
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger


fun StateManager.restoreSelectedMenuItem() : MenuItem {
    val savedSelectedMenuItem = dataRepository.localSettings.selectedMenuItem
    updateMasterState {
        it.copy(selectedMenuItem = savedSelectedMenuItem)
    }
    return savedSelectedMenuItem
}


suspend fun StateManager.getDataByMenuItem(menuItem: MenuItem) {
    var listData = dataRepository.getCountriesListData()
    if (menuItem == MenuItem.FAVORITES) {
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
    dataRepository.localSettings.selectedMenuItem = menuItem
}


fun StateManager.selectFavorite(country: String) {
    val favoriteCountries = dataRepository.localSettings.favoriteCountries
    if (favoriteCountries.containsKey(country)) {
        favoriteCountries.remove(country)
    } else {
        favoriteCountries[country] = true
    }
    updateMasterState {
        it.copy(favoriteCountries = favoriteCountries)
    }
    dataRepository.localSettings.favoriteCountries = favoriteCountries
}
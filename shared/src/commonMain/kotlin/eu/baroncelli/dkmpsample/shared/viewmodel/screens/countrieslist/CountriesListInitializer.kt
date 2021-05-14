package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getCountriesListData
import eu.baroncelli.dkmpsample.shared.datalayer.functions.getFavoriteCountriesMap
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation


// INIZIALIZATION function, called when the screen is first navigated to
// (it needs to be referenced in the "ScreenInit" file)

fun Navigation.countriesListInitializer(params: CountriesListParams) = screenCoroutine {
    var listData = dataRepository.getCountriesListData()
    val favorites = dataRepository.getFavoriteCountriesMap()
    if (params.listType == CountriesListType.FAVORITES) {
        // in case the Favorites tab is selected, only get the favorite countries
        listData = listData.filter { favorites.containsKey(it.name) }
    }
    // update state, after retrieving data from the repository
    stateManager.updateScreen(CountriesListState::class) {
        it.copy(
            isLoading = false,
            countriesListItems = listData,
            favoriteCountries = favorites,
        )
    }
}

package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getCountriesListData
import eu.baroncelli.dkmpsample.shared.datalayer.functions.getFavoriteCountriesMap
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.ScreenInitSettings


// INIZIALIZATION settings for this screen
// to understand the initialization behaviour, read the comments in the ScreenInitSettings.kt file

fun Navigation.initCountriesList(params: CountriesListParams) = ScreenInitSettings (
    initState = { CountriesListState(params = params, isLoading = true) },
    callOnInit = {
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
    },
    skipFirstRecompositionIfSameAsPreviousScreen = true, // this avoids showing the "loading..." message when changing tab selection
)
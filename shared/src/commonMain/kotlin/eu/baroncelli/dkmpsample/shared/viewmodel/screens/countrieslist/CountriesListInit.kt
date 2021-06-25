package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getCountriesListData
import eu.baroncelli.dkmpsample.shared.datalayer.functions.getFavoriteCountriesMap
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenParams
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable

// INIZIALIZATION settings for this screen
// this is what should be implemented:
// - a data class implementing the ScreenParams interface, which defines the parameters to the passed to the screen
// - Navigation extension function taking the ScreenParams class as an argument, return the ScreenInitSettings for this screen
// to understand the initialization behaviour, read the comments in the ScreenInitSettings.kt file

@Serializable // Note: ScreenParams should always be set as Serializable
data class CountriesListParams(val listType: CountriesListType) : ScreenParams

fun Navigation.initCountriesList(params: CountriesListParams) = ScreenInitSettings (
    title = "Countries: " + params.listType.name,
    initState = { CountriesListState(isLoading = true) },
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
    reinitOnEachNavigation = true, // in this way favourites can refresh
)
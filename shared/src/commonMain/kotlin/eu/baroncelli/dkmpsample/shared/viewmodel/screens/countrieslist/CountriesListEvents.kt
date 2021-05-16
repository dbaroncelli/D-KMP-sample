package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getFavoriteCountriesMap
import eu.baroncelli.dkmpsample.shared.viewmodel.Events



/********** EVENT functions, called directly by the UI layer **********/

fun Events.selectFavorite(countryName: String) = screenCoroutine {
    val favorites = dataRepository.getFavoriteCountriesMap(alsoToggleCountry = countryName)
    // update state with new favorites map, after toggling the value for the specified country
    stateManager.updateScreen(CountriesListState::class) {
        it.copy(favoriteCountries = favorites)
    }
}

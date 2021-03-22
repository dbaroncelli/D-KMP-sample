package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getCountryInfo
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager



suspend fun StateManager.updateCountryDetail(country: String) {
    // update CountryDetailState, after retrieving it from the Repository
    val listItemData = dataRepository.getCountryInfo(country)
    updateScreen(CountryDetailState::class) {
        it.copy(countryInfo = listItemData, isLoading = false)
    }
}
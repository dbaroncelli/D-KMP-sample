package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getCountryInfo
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation


// INIZIALIZATION function, called when the screen is first navigated to
// (it needs to be referenced in the "ScreenInit" file)

fun Navigation.countryDetailInitializer(params: CountryDetailParams) = screenCoroutine {
    val listItemData = dataRepository.getCountryInfo(params.countryName)
    // update state, after retrieving data from the repository
    stateManager.updateScreen(CountryDetailState::class) {
        it.copy(
            isLoading = false,
            countryInfo = listItemData,
        )
    }
}
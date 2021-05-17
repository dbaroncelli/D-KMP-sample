package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getCountryInfo
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.ScreenInitSettings



// INIZIALIZATION settings for this screen
// to understand the initialization behaviour, read the comments in the ScreenInitSettings.kt file

fun Navigation.initCountryDetail(params: CountryDetailParams) = ScreenInitSettings (
    initState = { CountryDetailState(params = params, isLoading = true) },
    callOnInit = {
        val countryInfo = dataRepository.getCountryInfo(params.countryName)
        // update state, after retrieving data from the repository
        stateManager.updateScreen(CountryDetailState::class) {
            it.copy(
                isLoading = false,
                countryInfo = countryInfo,
            )
        }
    },
)
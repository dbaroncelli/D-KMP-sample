package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider


fun StateProvider.getCountryDetailState(country : String) : CountryDetailState {

    var screenState = stateManager.getScreen(CountryDetailState::class)

    /********** RESET THE SCREEN STATE, BASED ON CONDITION **********/
    // make sure the condition is correct, otherwise the risk is to go into a recomposition loop
    val condition = screenState == null || country != screenState.countryName
    // in this case we reset the screen state, only if it hasn't been instantiated yet OR
    // the country passed as a parameter is different than the country currently set
    if (condition) {
        screenState = CountryDetailState(countryName = country, isLoading = true)
        stateManager.setScreen(screenState)
        events.loadCountryDetailData(country)
    }

    /********** RETURN STATE OBJECT **********/
    return screenState!!
}

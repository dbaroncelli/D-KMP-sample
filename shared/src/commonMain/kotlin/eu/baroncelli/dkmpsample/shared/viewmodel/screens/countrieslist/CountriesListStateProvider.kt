package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider


fun StateProvider.getCountriesListState() : CountriesListState {

    var screenState = stateManager.getScreen(CountriesListState::class)


    /********** RESET THE SCREEN STATE, BASED ON CONDITION **********/
    // make sure the condition is correct, otherwise the risk is to go into a recomposition loop
    val condition = screenState == null
    // in this case we reset the screen state, only if it hasn't been instantiated yet
    if (condition) {
        screenState = CountriesListState(isLoading = true)
        stateManager.setScreen(screenState)
        events.loadCountriesListData()
    }

    /********** RETURN STATE OBJECT **********/
    return screenState!!
}
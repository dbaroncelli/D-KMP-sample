package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider


fun StateProvider.getCountriesListState() : CountriesListState {

    var state = stateManager.getScreen(CountriesListState::class)


    /********** LOAD DATA, BASED ON CONDITION **********/
    // condition should be choosen so that the StateProvider doesn't attempt to reload the data when it's already there
    // make sure your condition is correct, otherwise the risk is to go into a recomposition loop
    val condition = state == null
    // in this case we load data, only if the Master screen hasn't yet been instantiated
    //debugLogger.log("MasterStateProvide: "+state?.selectedMenuItem+" / LOAD DATA "+ condition)
    if (condition) {
        state = CountriesListState(isLoading = true)
        stateManager.setScreen(state)
        events.loadCountriesListData()
    }

    /********** RETURN STATE OBJECT **********/
    return state!!
}
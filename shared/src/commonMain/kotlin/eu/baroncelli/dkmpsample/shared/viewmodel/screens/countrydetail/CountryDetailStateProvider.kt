package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider

fun StateProvider.getCountryDetailState(country : String) : CountryDetailState {


    var state = stateManager.getScreen(CountryDetailState::class) as? CountryDetailState


    /********** LOAD DATA, BASED ON CONDITION **********/
    // condition should be choosen so that the StateProvider doesn't attempt to reload the data when it's already there
    // make sure your condition is correct, otherwise the risk is to go into a recomposition loop
    val condition = state == null || state.countryName != country
    // in this case we load data, if the Detail screen hasn't yet been instantiated OR
    // the country parameter that has been passed is different than the one currently set
    //debugLogger.log("DetailStateProvide: "+state?.countryName+" / LOAD DATA "+ condition)
    if (condition) {
        state = CountryDetailState(countryName = country, isLoading = true)
        stateManager.setScreen(state)
        events.loadCountryDetailData(country)
    }

    /********** RETURN STATE OBJECT **********/
    return state!!
}

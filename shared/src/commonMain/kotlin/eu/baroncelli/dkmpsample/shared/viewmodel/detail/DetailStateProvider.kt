package eu.baroncelli.dkmpsample.shared.viewmodel.detail

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger

fun StateProvider.getDetail(country : String) : DetailState {

    /********** LOAD DATA, BASED ON CONDITION **********/
    // condition should be choosen so that the StateProvider doesn't attempt to reload the data when it's already there
    // make sure your condition is correct, otherwise the risk is to go into a recomposition loop
    val condition = getState().detailState.countryName != country
    // in this case we load data only if the country parameter that has been passed
    // is different than the one currently set in the state object
    //debugLogger.d{"DetailStateProvide: "+getState().detailState.detailName+" / LOAD DATA "+ condition}
    if (condition) {
        events.loadDetailData(country)
    }

    /********** RETURN STATE OBJECT **********/
    return getState().detailState
}

package eu.baroncelli.dkmpsample.shared.viewmodel.detail

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger

fun StateProvider.getDetail(country : String) : DetailState {

    /********** LOAD DATA, BASED ON CONDITION **********/
    // condition should be set to that the StateProvider should load the data only once
    // make sure your condition is correct, otherwise the risk is to load data at each single
    // declarative UI recomposition, which you definitely want to avoid!
    val condition = getState().detailState.countryName != country
    //debugLogger.d{"DetailStateProvide: "+getState().detailState.detailName+" / LOAD DATA "+ condition}
    if (condition) {
        events.loadDetailData(country)
    }

    /********** RETURN STATE OBJECT **********/
    return getState().detailState
}

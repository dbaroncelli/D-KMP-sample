package eu.baroncelli.dkmpsample.shared.viewmodel.detail

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger

fun StateProvider.getDetail(country : String) : DetailState {

    /********** RUN UPDATE, BASED ON CONDITION **********/
    val condition = getState().detailState.detailName != country
    debugLogger.d{"getDetail() country: "+getState().detailState.detailName+" / RUN UPDATE "+ condition}
    if (condition) {
        events.updateDetailData(country)
    }

    /********** RETURN STATE OBJECT **********/
    return getState().detailState
}

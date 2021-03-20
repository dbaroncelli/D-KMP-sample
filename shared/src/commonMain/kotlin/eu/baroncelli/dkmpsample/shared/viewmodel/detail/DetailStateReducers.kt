package eu.baroncelli.dkmpsample.shared.viewmodel.detail

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getCountryInfo
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager

/********** LAMBDA FUNCTION, USED BY THE STATE REDUCERS **********/

fun StateManager.updateDetailState(block: (DetailState) -> DetailState) {
    //debugLogger.d {"changed detail state"}
    state = state.copy(detailState = block(state.detailState))
}



/********** STATE REDUCERS **********/

fun StateManager.setDetailLoading(country : String) {
    // set the countryName and the isLoading flag to true in the state object
    updateDetailState {
        it.copy(countryName = country, isLoading = true)
    }
}

suspend fun StateManager.setDetailData(country: String) {
    // set detail data into the state object, after retrieving it from the Repository
    val listItemData = dataRepository.getCountryInfo(country)
    updateDetailState {
        it.copy(countryInfo = listItemData, isLoading = false)
    }
}
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
    // set the countryName property and set the isLoading flag to true
    updateDetailState {
        it.copy(countryName = country, isLoading = true)
    }
}

suspend fun StateManager.getDetails(country: String) {
    // get country data from the Repository
    val listItemData = dataRepository.getCountryInfo(country)
    updateDetailState {
        it.copy(countryInfo = listItemData, isLoading = false)
    }
}
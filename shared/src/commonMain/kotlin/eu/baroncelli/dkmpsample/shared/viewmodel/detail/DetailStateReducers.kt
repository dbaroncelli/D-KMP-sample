package eu.baroncelli.dkmpsample.shared.viewmodel.detail

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getCountryInfo
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager

/********** LAMBDA FUNCTION **********/

fun StateManager.updateDetailState(block: (DetailState) -> DetailState) {
    //debugLogger.d {"changed detail state"}
    state = state.copy(detailState = block(state.detailState))
}



/********** STATE REDUCERS **********/

fun StateManager.setDetailLoading(country : String) {
    updateDetailState {
        it.copy(countryName = country, isLoading = true)
    }
}

suspend fun StateManager.getDetails(nome: String) {
    val listItemData = dataRepository.getCountryInfo(nome)
    updateDetailState {
        it.copy(countryInfo = listItemData, isLoading = false)
    }
}
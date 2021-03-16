package eu.baroncelli.dkmpsample.shared.viewmodel.statereducers

import eu.baroncelli.dkmpsample.shared.datalayer.functions.getCountryInfo
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager


fun StateManager.setDetailLoading() {
    updateDetailState {
        it.copy(isLoading = true)
    }
}

suspend fun StateManager.getDetails(nome: String) {
    val listItemData = dataRepository.getCountryInfo(nome)
    updateDetailState {
        it.copy(countryInfo = listItemData, isLoading = false)
    }
}
package eu.baroncelli.dkmpsample.shared.datalayer.functions

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.apis.fetchCountriesList
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger
import eu.baroncelli.dkmpsample.shared.viewmodel.master.CountriesListItem


suspend fun Repository.getCountriesListData(): List<CountriesListItem> {


    if (Repository.countriesList.isEmpty()) {
        webservices.fetchCountriesList()?.apply {
            Repository.countriesList = data.sortedByDescending { it.firstDosesPercentageFloat }
            if (error!=null) {
                debugLogger.d { "ERROR MESSAGE: $error"}
            }
        }
    }

    return Repository.countriesList.map { elem -> CountriesListItem(_data = elem) }.toList()

}
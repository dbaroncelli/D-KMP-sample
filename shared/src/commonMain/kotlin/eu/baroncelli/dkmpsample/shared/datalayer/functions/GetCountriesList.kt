package eu.baroncelli.dkmpsample.shared.datalayer.functions

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localdb.countries.getCountriesList
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localdb.countries.setCountriesList
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.apis.fetchCountriesList
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListItem
import kotlinx.datetime.Clock

suspend fun Repository.getCountriesListData(): List<CountriesListItem> = withRepoContext {

    val nowUnixtime = Clock.System.now().epochSeconds

    // WEBSERVICE call, to fetch the countries list data
    //      DATA STORE: localDb
    //      FETCH CONDITION: database cache is over 1 hour old
    if (nowUnixtime - localSettings.listCacheTimestamp > 60 * 60) {
        webservices.fetchCountriesList()?.apply {
            debugLogger.log("countriesList FETCHED FROM WEBSERVICES")
            if (error == null) {
                localDb.setCountriesList(data.sortedByDescending { it.firstDosesPercentageFloat })
                localSettings.listCacheTimestamp = nowUnixtime
            } else {
                debugLogger.log("ERROR MESSAGE: $error")
            }
        }
    }

    // RETURN a list where:
    //  the datalayer list object (CountryListData) has been mapped to the viewmodel list object (CountriesListItem)
    localDb.getCountriesList().map { elem ->
        CountriesListItem(_data = elem)
    }.toList()
}
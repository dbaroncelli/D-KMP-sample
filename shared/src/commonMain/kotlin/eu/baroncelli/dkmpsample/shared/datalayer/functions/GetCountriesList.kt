package eu.baroncelli.dkmpsample.shared.datalayer.functions

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localdb.countries.getCountriesList
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localdb.countries.setCountriesList
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.apis.fetchCountriesList
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlin.time.measureTime


suspend fun Repository.getCountriesListData(): List<CountriesListItem> = withRepoContext {

    val nowUnixtime = Clock.System.now().epochSeconds

    // we get the countries list:
    // - we get it fresh from the webservices only if the database cache is over 1 hour old
    if (nowUnixtime-localSettings.listCacheTimestamp > 60*60) {
        webservices.fetchCountriesList()?.apply {
            debugLogger.log("countriesList FETCHED FROM WEBSERVICES")
            if (error==null) {
                localDb.setCountriesList(data.sortedByDescending { it.firstDosesPercentageFloat })
                localSettings.listCacheTimestamp = nowUnixtime
            } else {
                debugLogger.log ("ERROR MESSAGE: $error")
            }
        }
    }
    localDb.getCountriesList().map { elem -> CountriesListItem(_data = elem) }.toList()
    // we are mapping the datalayer list row object (CountryListData) to the viewmodel list row object (CountriesListItem)
}
package eu.baroncelli.dkmpsample.shared.datalayer.functions

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localdb.countries.getCountriesList
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.apis.fetchCountryExtraData
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryInfo

suspend fun Repository.getCountryInfo(country: String): CountryInfo = withRepoContext {

    // WEBSERVICE call, to fetch the country extra data
    //      DATA STORE: runtimeCache
    //      FETCH CONDITION: it's not in the runtimeCache
    if (!runtimeCache.countryExtraData.containsKey(country)) {
        webservices.fetchCountryExtraData(country)?.apply {
            runtimeCache.countryExtraData[country] = data
        }
    }

    // RETURN a CountryInfo object, whose constructor takes 2 datalayer objects:
    //  - CountriesListData (read from the localDb)
    //  - CountriesExtraData (read from the runtimeCache)
    CountryInfo(
        localDb.getCountriesList().first{it.name==country},
        runtimeCache.countryExtraData[country],
    )
}
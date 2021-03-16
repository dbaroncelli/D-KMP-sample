package eu.baroncelli.dkmpsample.shared.datalayer.functions

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.apis.fetchCountryExtraData
import eu.baroncelli.dkmpsample.shared.viewmodel.appstate.detail.*

suspend fun Repository.getCountryInfo(country: String): CountryInfo {

    if (!Repository.countryExtraData.containsKey(country)) {
        webservices.fetchCountryExtraData(country)?.apply {
            Repository.countryExtraData[country] = data
        }
    }

    return CountryInfo(Repository.countriesList.first{it.name==country}, Repository.countryExtraData[country])

}
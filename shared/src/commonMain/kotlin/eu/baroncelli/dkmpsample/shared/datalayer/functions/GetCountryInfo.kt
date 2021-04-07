package eu.baroncelli.dkmpsample.shared.datalayer.functions

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localdb.countries.getCountriesList
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.apis.fetchCountryExtraData
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryInfo

suspend fun Repository.getCountryInfo(country: String): CountryInfo = withRepoContext {

    // we get the country extra data, which we just keep in the runtime cache, not in the database
    if (!Repository.countryExtraData.containsKey(country)) {
        webservices.fetchCountryExtraData(country)?.apply {
            Repository.countryExtraData[country] = data
        }
    }
    CountryInfo(localDb.getCountriesList().first{it.name==country}, Repository.countryExtraData[country])
    // CountryInfo is a ViewModel class whose constructor takes 2 datalayer objects: CountriesListData and CountriesExtraData
}
package eu.baroncelli.dkmpsample.shared.datalayer.functions

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localdb.countries.getFavoriteCountriesMap
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localdb.countries.toggleFavoriteCountry

suspend fun Repository.getFavoriteCountriesMap(toggleCountry : String? = null): Map<String,Boolean> = withRepoContext {
    if (toggleCountry != null) {
        localDb.toggleFavoriteCountry(toggleCountry)
    }
    localDb.getFavoriteCountriesMap()
    // returns a "trueMap" (i.e. the values are always TRUE boolean)
    // where the keys are the names of the favorite countries
}
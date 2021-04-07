package eu.baroncelli.dkmpsample.shared.datalayer.sources.localdb.countries

import eu.baroncelli.dkmpsample.shared.datalayer.objects.CountryListData
import mylocal.db.LocalDb

fun LocalDb.getCountriesList() : List<CountryListData> {
    return countriesQueries.getCountriesList(mapper = ::CountryListData).executeAsList()
}

fun LocalDb.setCountriesList(list : List<CountryListData>) {
    countriesQueries.transaction {
        list.forEach {
            countriesQueries.updateCountry(
                name = it.name,
                population = it.population,
                first_doses = it.firstDoses,
                fully_vaccinated = it.fullyVaccinated,
            )
        }
    }
}

fun LocalDb.toggleFavoriteCountry(country : String) {
    countriesQueries.updateFavorite(country)
}

fun LocalDb.getFavoriteCountriesMap() : Map<String,Boolean> {
    return countriesQueries.getFavorites().executeAsList().associateBy({it}, {true})
}
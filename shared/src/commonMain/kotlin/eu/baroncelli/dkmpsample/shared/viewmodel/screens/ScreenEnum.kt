package eu.baroncelli.dkmpsample.shared.viewmodel.screens

import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.initCountriesList
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.initCountryDetail


// DEFINITION OF ALL SCREENS IN THE APP

enum class Screen(
    val asString: String,
    val navigationLevel : Int = 1,
    val initSettings: Navigation.(ScreenIdentifier) -> ScreenInitSettings,
    val stackableInstances : Boolean = false,
) {
    CountriesList("countrieslist", 1, { initCountriesList(it.params()) }, true),
    CountryDetail("country", 2, { initCountryDetail(it.params()) }),
}
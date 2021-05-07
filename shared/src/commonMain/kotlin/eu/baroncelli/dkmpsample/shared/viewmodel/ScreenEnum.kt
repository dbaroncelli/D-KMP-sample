package eu.baroncelli.dkmpsample.shared.viewmodel

internal val startScreen = Screen.CountriesList // the start screen should be specified here

enum class Screen(val route: String, val requiresInstanceId : Boolean = false) {
    CountriesList("countrieslist"),
    CountryDetail("country", true),
}
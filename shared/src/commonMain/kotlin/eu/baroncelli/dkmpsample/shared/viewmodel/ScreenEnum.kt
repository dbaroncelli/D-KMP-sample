package eu.baroncelli.dkmpsample.shared.viewmodel

internal val startScreen = Screen.CountriesList // the start screen should be specified here

enum class Screen(val route: String) {
    CountriesList("countrieslist"),
    CountryDetail("country"),
}
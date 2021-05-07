package eu.baroncelli.dkmpsample.shared.viewmodel

internal val startScreen = Screen.CountriesList // the start screen should be specified here

// if you want to support a "two pane" visualization on larger devices:
// for each detail screen (to be shown on the right) define a master screen (to be shown on the left)
// N.B. "two pane" visualization is currently not provided by the architecture, but it will in the future

enum class Screen(val route: String, val requiresInstanceId : Boolean = false, val masterScreen: Screen? = null) {
    CountriesList("countrieslist"),
    CountryDetail("country", true, CountriesList),
}
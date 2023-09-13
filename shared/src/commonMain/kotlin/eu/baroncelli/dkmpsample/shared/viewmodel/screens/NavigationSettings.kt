package eu.baroncelli.dkmpsample.shared.viewmodel.screens

import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen.CountriesList
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListType.ALL
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListType.FAVORITES


// CONFIGURATION SETTINGS

object navigationSettings {
    val homeScreen = Level1Navigation.AllCountries // the start screen should be specified here
    val saveLastLevel1Screen = true
    val alwaysQuitOnHomeScreen = true
}


// LEVEL 1 NAVIGATION OF THE APP

enum class Level1Navigation(val screenIdentifier: ScreenIdentifier, val rememberVerticalStack: Boolean = false) {
    AllCountries(ScreenIdentifier.get(CountriesList, CountriesListParams(listType = ALL)), true),
    FavoriteCountries(ScreenIdentifier.get(CountriesList, CountriesListParams(listType = FAVORITES)), true),
}
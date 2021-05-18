package eu.baroncelli.dkmpsample.shared.viewmodel.screens

import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen.*
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListParams
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListType.*


// CONFIGURATION SETTINGS

object navigationSettings {
    val homeScreen = Level1Navigation.AllCountries // the start screen should be specified here
    val saveLastLevel1Screen = true
    val alwaysQuitOnHomeScreen = true
}


// LEVEL 1 NAVIGATION OF THE APP

enum class Level1Navigation(val screenIdentifier: ScreenIdentifier, val rememberVerticalStack: Boolean = false) {
    AllCountries( ScreenIdentifier.get(CountriesList, CountriesListParams(listType = ALL))),
    FavoriteCountries( ScreenIdentifier.get(CountriesList, CountriesListParams(listType = FAVORITES))),
}
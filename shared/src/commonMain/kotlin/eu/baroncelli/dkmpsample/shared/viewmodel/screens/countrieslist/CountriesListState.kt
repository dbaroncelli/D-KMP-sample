package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.apis.CountryListData
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenState
import eu.baroncelli.dkmpsample.shared.viewmodel.utils.toPercentageString


/********** STATE CLASS DEFINITION **********/

data class CountriesListState (
    val isLoading: Boolean = false,
    val selectedMenuItem : MenuItem = MenuItem.ALL,
    val countriesListItems : List<CountriesListItem> = emptyList(),
    val favoriteCountries : Map<String,Boolean> = emptyMap(),
): ScreenState


/********** PROPERTY CLASSES DEFINITION **********/


enum class MenuItem { ALL, FAVORITES }

class CountriesListItem (
    _data : CountryListData,
) {
    // in the ViewModel classes, our computed properties only do UI-formatting operations
    // (the arithmetical operations, such as calculating a percentage, should happen in the DataLayer classes)
    val name = _data.name
    val firstDosesPerc = _data.firstDosesPercentageFloat.toPercentageString()
    val fullyVaccinatedPerc = _data.fullyVaccinatedPercentageFloat.toPercentageString()
}
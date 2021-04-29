package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist

import eu.baroncelli.dkmpsample.shared.datalayer.objects.CountryListData
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenState
import eu.baroncelli.dkmpsample.shared.viewmodel.utils.toPercentageString

// here is the data class defining the state for this screen

data class CountriesListState (
    val isLoading: Boolean = false,
    val selectedMenuItem : MenuItem = MenuItem.ALL,
    val countriesListItems : List<CountriesListItem> = emptyList(),
    val favoriteCountries : Map<String,Boolean> = emptyMap(),
): ScreenState



/********** property classes **********/

enum class MenuItem { ALL, FAVORITES }

data class CountriesListItem (
    val _data : CountryListData,
) {
    // in the ViewModel classes, our computed properties only do UI-formatting operations
    // (the arithmetical operations, such as calculating a percentage, should happen in the DataLayer classes)
    val name = _data.name
    val firstDosesPerc = _data.firstDosesPercentageFloat.toPercentageString()
    val fullyVaccinatedPerc = _data.fullyVaccinatedPercentageFloat.toPercentageString()
}
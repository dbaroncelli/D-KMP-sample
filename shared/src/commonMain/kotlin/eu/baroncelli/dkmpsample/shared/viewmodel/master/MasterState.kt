package eu.baroncelli.dkmpsample.shared.viewmodel.master

import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.apis.CountryListData
import eu.baroncelli.dkmpsample.shared.viewmodel.utils.toPercentageString


/********** STATE CLASS DEFINITION **********/

data class MasterState (
    val selectedMenuItem : MenuItem = MenuItem.UNDEFINED,
    val countriesList : List<CountriesListItem> = emptyList(),
    val isLoading : Boolean = true,
    val favoriteCountries : Map<String,Boolean> = emptyMap(),
)


/********** PROPERTY CLASSES DEFINITION **********/


enum class MenuItem { UNDEFINED, ALL, FAVORITES }

class CountriesListItem (
    _data : CountryListData,
) {
    // in the ViewModel classes, our computed properties only do UI-formatting operations
    // (the arithmetical operations, such as calculating a percentage, should happen in the DataLayer classes)
    val name = _data.name
    val firstDosesPerc = _data.firstDosesPercentageFloat.toPercentageString()
    val fullyVaccinatedPerc = _data.fullyVaccinatedPercentageFloat.toPercentageString()
}
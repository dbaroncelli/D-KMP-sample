package eu.baroncelli.dkmpsample.shared.viewmodel.appstate.master




data class MasterState (
    val selectedMenuItem : MenuItem = MenuItem.ALL,
    val countriesList : List<CountriesListItem> = emptyList(),
    val isLoading : Boolean = true,
    val favoriteCountries : Map<String,Boolean> = mutableMapOf(),
)


enum class MenuItem { ALL, FAVORITES }
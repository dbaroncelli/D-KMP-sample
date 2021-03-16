package eu.baroncelli.dkmpsample.shared.viewmodel.appstate.detail


data class DetailState (
    val countryInfo : CountryInfo = CountryInfo(),
    val isLoading : Boolean = false,
)
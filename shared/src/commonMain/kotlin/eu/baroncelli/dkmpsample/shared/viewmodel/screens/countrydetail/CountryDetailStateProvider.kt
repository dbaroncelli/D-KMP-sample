package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider


fun StateProvider.getCountryDetailState(country : String) : CountryDetailState {

    return stateManager.getScreen(
        initState = { CountryDetailState(countryName = country, isLoading = true) },
        callOnInit = { events.loadCountryDetailData(country) },
        reinitWhen = { country != it.countryName }
    )

}

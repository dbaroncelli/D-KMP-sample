package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider


fun StateProvider.getCountryDetailState(country : String) : CountryDetailState {

    // the state gets initialized the first time this function gets called
    // and if the "reinitWhen" condition is true
    // the code in "callOnInit" is called on initialization
    return stateManager.getScreen(
        initState = { CountryDetailState(countryName = country, isLoading = true) },
        callOnInit = { events.loadCountryDetailData(country) },
        reinitWhen = { country != it.countryName }
    )

}
package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail

import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider


fun StateProvider.getCountryDetailState(country : String) : CountryDetailState {

    // the screen gets initialized the first time it gets called
    // and if the "reinitWhen" condition is true
    return stateManager.getScreen(
        initState = { CountryDetailState(countryName = country, isLoading = true) },
        callOnInit = { events.loadCountryDetailData(country) },
        reinitWhen = { country != it.countryName }
    )

}
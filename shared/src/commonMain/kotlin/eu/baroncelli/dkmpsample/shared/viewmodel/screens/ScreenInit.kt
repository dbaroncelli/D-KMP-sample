package eu.baroncelli.dkmpsample.shared.viewmodel.screens

import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenInitInfo
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.countriesListInitializer
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.countryDetailInitializer

/* INITALIZATION BEHAVIOUR:
when a screen is first navigated to, using "dkmpNav.navigate()", this is what happens in sequence:
1. The screen state is initialized with the value defined in "defaultState" (typically including "isLoading=true"), so that the UI can show a "loading..." message
2. A first UI recomposition is triggered, so that the new state can be displayed on the UI layer
3. After recomposition, the function defined in "callOnInit" is called, which typically loads the data from the Repository
4. The "callOnInit" function typically makes a call to "stateManager.updateScreen()", which updates the state and triggers a second UI recomposition

When calling "dkmpNav.navigate(screen,params)" to navigate to a screen of the same kind (i.e. same "screen" but different "params")
and the Repository functions called inside the "callOnInit" function don't depend on "params",
it typically means that the Repository functions will not make a network call, but get the previously retrieved data from the local cache.
In these specific cases, it make sense to skip the first recomposition, as it would avoid showing the "loading..." message for a fraction of a second.
In order to achieve this behaviour, just set the flag "skipDefaultStateRecompositionIfSameAsPreviousScreen" to true for such screen.
*/



fun Navigation.getScreenInitInfo(screenIdentifier: ScreenIdentifier) : ScreenInitInfo {

    return when (screenIdentifier.screen) {

        Screen.CountriesList -> ScreenInitInfo (
            defaultState = CountriesListState(params = screenIdentifier.params(), isLoading = true),
            callOnInit =  { countriesListInitializer(it.params()) },
            skipDefaultStateRecompositionIfSameAsPreviousScreen = true,
        )

        Screen.CountryDetail -> ScreenInitInfo (
            defaultState = CountryDetailState(params = screenIdentifier.params(), isLoading = true),
            callOnInit = { countryDetailInitializer(it.params()) },
        )


    }

}
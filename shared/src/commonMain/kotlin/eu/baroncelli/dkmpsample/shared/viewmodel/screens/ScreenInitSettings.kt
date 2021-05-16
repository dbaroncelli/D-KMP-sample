package eu.baroncelli.dkmpsample.shared.viewmodel.screens

import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenState
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager


/* INITALIZATION BEHAVIOUR (two UI recompositions):
when a screen is first navigated to, using "dkmpNav.navigate(screen,params)", this is what happens in sequence:
1. The screen state is initialized with the value defined in "initState" (typically it includes "isLoading=true"), so that the UI can show a "loading..." message
2. The FIRST recomposition is triggered, so that the UI layer displays the new state
3. After recomposition, the function defined in "callOnInit" is called, which typically loads the data from the Repository
4. The "callOnInit" function typically makes a call to "stateManager.updateScreen()", which updates the state and hence triggers the SECOND recomposition */


class ScreenInitSettings (
    val initState : (ScreenIdentifier) -> ScreenState,
    val callOnInit : suspend (StateManager) -> Unit,
    val skipFirstRecompositionIfSameAsPreviousScreen: Boolean = false,
    /* use cases for skipFirstRecompositionIfSameAsPreviousScreen = true:
        When calling "dkmpNav.navigate(screen,params)" to navigate to a screen of the same kind (i.e. same "screen" but different "params")
        AND the Repository functions called inside the "callOnInit" function don't depend on "params",
        it typically means that the Repository functions will not make a network call, but will retrieve the data from the local cache.
        In this specific case, it makes sense to skip the first recomposition, as it avoids showing the "loading..." message for a fraction of a second.
        In order to achieve this behaviour, just set the flag "skipFirstRecompositionIfSameAsPreviousScreen" to true for such screen. */
    val callOnInitAlsoAfterBackground : Boolean = false,
    /* use cases for callOnInitAlsoAfterBackground = true:
        By default, the "callOnInit" function is not called again when the app comes back from the background.
        However in use cases such as "polling", you might want to call "callOnInit" again.
        In order to achieve this behaviour, just set the flag "callOnInitAlsoAfterBackground" to true for such screen. */
)
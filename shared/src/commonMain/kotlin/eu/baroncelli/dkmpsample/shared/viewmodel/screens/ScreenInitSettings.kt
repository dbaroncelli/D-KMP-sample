package eu.baroncelli.dkmpsample.shared.viewmodel.screens

import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenState
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager


/* INITALIZATION BEHAVIOUR (two UI recompositions):
when a screen is first navigated to, using "dkmpNav.navigate(screen,params)", this is what happens in sequence:
1. The screen state is initialized with the value defined in "initState" (typically it includes "isLoading=true"), so that the UI can show a "loading..." message
2. The FIRST recomposition is triggered, so that the UI layer displays the "initState"
3. After recomposition, the function defined in "callOnInit" is called, which typically loads the data from the Repository
4. The "callOnInit" function typically makes a call to "stateManager.updateScreen()", which updates the state and hence triggers the SECOND recomposition */


class ScreenInitSettings(
    val title: String,
    val initState: (ScreenIdentifier) -> ScreenState,
    val callOnInit: suspend (StateManager) -> Unit,
    val runCallOnInitBeforeNavigationTransition: Boolean = false,
    /* use cases for runCallOnInitBeforeNavigationTransition = true:
        By default, "callOnInit" is called after the navigation transition, i.e. when screen is already presented with the "initState".
        This causes "callOnInit" to trigger a new recomposition, when the screenState is updated.
        If the code in "callOnInit" is not heavy, you might prefer to present the screen only after the execution of "callOnInit" is completed, avoiding an extra recomposition. */
    val reinitOnEachNavigation: Boolean = false,
    /* use cases for reinitOnEachNavigation = true:
        By default, if the screen is already in the backstack, it doesn't get reinitialized if it becomes active again.
        However if you want to refresh it each time it becomes active, you might want to reinitialize it again. */
    val callOnInitAlsoAfterBackground: Boolean = false,
    /* use cases for callOnInitAlsoAfterBackground = true:
        By default, the "callOnInit" function is not called again when the app comes back from the background.
        However in use cases when data might have changed in the meantime, you might want to call "callOnInit" again. */
)
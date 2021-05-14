package eu.baroncelli.dkmpsample.android

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import eu.baroncelli.dkmpsample.shared.viewmodel.Events
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.StateProviders

@Composable
fun Navigation.Router(
    stateProviders: StateProviders,
    events: Events,
) {
    BackHandler { // catching the back button to update the DKMPViewModel
        exitScreen()
    }
    val screenUIsStateHolder = rememberSaveableStateHolder()
    screenUIsStateHolder.SaveableStateProvider(currentScreenIdentifier.URI) {
        ScreenPicker(currentScreenIdentifier, stateProviders, events)
    }
    screenUIsToForget.forEach {
        screenUIsStateHolder.removeState(it.URI)
    }
}





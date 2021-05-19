package eu.baroncelli.dkmpsample.android.composables.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import eu.baroncelli.dkmpsample.shared.viewmodel.*

@Composable
fun Navigation.Router(
    stateProvider: StateProvider,
    events: Events,
) {

    val screenUIsStateHolder = rememberSaveableStateHolder()

    if (!only1ScreenInBackstack) {
        BackHandler { // catching the back button to update the DKMPViewModel
            exitScreen()
        }
    }

    screenUIsStateHolder.SaveableStateProvider(currentScreenIdentifier.URI) {
        ScreenPicker(currentScreenIdentifier, stateProvider, events)
    }

    getScreenUIsToForget().forEach {
        screenUIsStateHolder.removeState(it.URI)
        Log.d("D-KMP", "removed UI screen "+it.URI)
    }


}
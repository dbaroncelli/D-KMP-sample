package eu.baroncelli.dkmpsample.android.composables.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.unit.dp
import eu.baroncelli.dkmpsample.android.composables.navigation.templates.OnePane
import eu.baroncelli.dkmpsample.android.composables.navigation.templates.TwoPane
import eu.baroncelli.dkmpsample.shared.viewmodel.*

@Composable
fun Navigation.Router(
    stateProvider: StateProvider,
    events: Events,
) {

    val screenUIsStateHolder = rememberSaveableStateHolder()

    val twopaneWidthThreshold = 1000.dp
    BoxWithConstraints() {
        if (maxWidth < maxHeight || maxWidth<twopaneWidthThreshold) {
            OnePane(screenUIsStateHolder, stateProvider, events)
        } else {
            TwoPane(screenUIsStateHolder, stateProvider, events)
        }
    }

    screenStatesToRemove.forEach {
        screenUIsStateHolder.removeState(it.URI)
        Log.d("D-KMP", "removed UI screen "+it.URI)
    }

    if (!only1ScreenInBackstack) {
        BackHandler { // catching the back button to update the DKMPViewModel
            exitScreen()
        }
    }

}
package eu.baroncelli.dkmpsample.android.composables.navigation.templates

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.SaveableStateHolder
import eu.baroncelli.dkmpsample.android.composables.navigation.ScreenPicker
import eu.baroncelli.dkmpsample.android.composables.screens.countrieslist.Level1BottomBar
import eu.baroncelli.dkmpsample.shared.viewmodel.Events
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider

@Composable
fun Navigation.OnePane(
    saveableStateHolder: SaveableStateHolder,
    stateProvider: StateProvider,
    events: Events,
) {

    Scaffold(
        topBar = { TopBar(title) },
        content = {
            saveableStateHolder.SaveableStateProvider(currentScreenIdentifier.URI) {
                ScreenPicker(currentScreenIdentifier, stateProvider, events)
            }
        },
        bottomBar = { if (currentScreenIdentifier.screen.navigationLevel == 1) Level1BottomBar(currentScreenIdentifier) }
    )
}
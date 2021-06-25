package eu.baroncelli.dkmpsample.desktop.composables.navigation.templates

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.SaveableStateHolder
import eu.baroncelli.dkmpsample.desktop.composables.navigation.ScreenPicker
import eu.baroncelli.dkmpsample.desktop.composables.screens.countrieslist.Level1BottomBar
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation

@Composable
fun Navigation.OnePane(
    saveableStateHolder: SaveableStateHolder
) {
    Scaffold(
        topBar = { TopBar(getTitle(currentScreenIdentifier)) },
        content = {
            saveableStateHolder.SaveableStateProvider(currentScreenIdentifier.URI) {
                ScreenPicker(currentScreenIdentifier)
            }
        },
        bottomBar = { if (currentScreenIdentifier.screen.navigationLevel == 1) Level1BottomBar(currentScreenIdentifier) }
    )
}
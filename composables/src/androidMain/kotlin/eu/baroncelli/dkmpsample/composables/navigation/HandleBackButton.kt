package eu.baroncelli.dkmpsample.composables.navigation

import androidx.compose.runtime.Composable
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.saveable.SaveableStateHolder
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger

@Composable
actual fun Navigation.HandleBackButton(
    saveableStateHolder: SaveableStateHolder,
    localNavigationState: MutableState<LocalNavigationState>
) {
    BackHandler { // catching the back button to update the DKMPViewModel
        val navState = localNavigationState.value
        val originScreenIdentifier = navState.topScreenIdentifier
        exitScreen(originScreenIdentifier) // shared state changed
        debugLogger.log("UI NAVIGATION RECOMPOSITION: back button from "+originScreenIdentifier.URI)
        if (navState.path.isEmpty()) {
            localNavigationState.value = navState.copy(level1ScreenIdentifier = stateManager.currentLevel1ScreenIdentifier!!)
        } else {
            localNavigationState.value = navState.copy(path = navState.path - originScreenIdentifier )
        }
        debugLogger.log("    to "+localNavigationState.value.level1ScreenIdentifier.URI)
        saveableStateHolder.removeState(originScreenIdentifier)
    }
}
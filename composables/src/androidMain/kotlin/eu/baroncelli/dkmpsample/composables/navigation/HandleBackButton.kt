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
    BackHandler(!localNavigationState.value.nextBackQuitsApp) { // catching the back button
        val navState = localNavigationState.value
        val originScreenIdentifier = navState.topScreenIdentifier
        exitScreen(originScreenIdentifier) // change the shared state
        debugLogger.log("UI NAVIGATION RECOMPOSITION: back button from "+originScreenIdentifier.URI)
        if (navState.path.isEmpty()) {
            localNavigationState.value = navState.copy(
                level1ScreenIdentifier = stateManager.currentLevel1ScreenIdentifier!!,
                nextBackQuitsApp = nextBackQuitsApp
            )
        } else {
            localNavigationState.value = navState.copy(
                path = (navState.path - originScreenIdentifier).toMutableList(),
                nextBackQuitsApp = nextBackQuitsApp
            )
        }
        debugLogger.log("    to "+localNavigationState.value.level1ScreenIdentifier.URI)
        saveableStateHolder.removeState(originScreenIdentifier)
    }
}
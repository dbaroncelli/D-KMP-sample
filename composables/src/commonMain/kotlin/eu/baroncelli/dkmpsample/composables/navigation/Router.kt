package eu.baroncelli.dkmpsample.composables.navigation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.unit.dp
import eu.baroncelli.dkmpsample.composables.navigation.templates.OnePane
import eu.baroncelli.dkmpsample.composables.navigation.templates.TwoPane
import eu.baroncelli.dkmpsample.shared.viewmodel.*
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Level1Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen

data class LocalNavigationState (
    val level1ScreenIdentifier : ScreenIdentifier,
    var path : MutableList<ScreenIdentifier>, // path is the backstack without the level1ScreenIdentifier
    val nextBackQuitsApp: Boolean
) {
    val topScreenIdentifier = if (path.isEmpty()) level1ScreenIdentifier else path.last()
}


@Composable
fun Navigation.Router() {

    val screenUIsStateHolder = rememberSaveableStateHolder()
    val localNavigationState = remember { mutableStateOf( getStartNavigationState() ) }


    val twopaneWidthThreshold = 1000.dp
    BoxWithConstraints {
        if (maxWidth < maxHeight || maxWidth<twopaneWidthThreshold) {
            OnePane(screenUIsStateHolder, localNavigationState)
        } else {
            TwoPane(screenUIsStateHolder, localNavigationState)
        }
    }

    HandleBackButton(screenUIsStateHolder, localNavigationState)

}

fun Navigation.getStartNavigationState() : LocalNavigationState {
    val screenIdentifier = getStartScreenIdentifier()
    selectLevel1Navigation(screenIdentifier)
    return LocalNavigationState(screenIdentifier, mutableListOf(), nextBackQuitsApp)
}


fun Navigation.navigationProcessor(localNavigationState: MutableState<LocalNavigationState>) : (Screen, ScreenParams?) -> Unit {
    return { screen, screenParams ->
        val screenIdentifier = ScreenIdentifier.get(screen, screenParams)
        debugLogger.log("UI NAVIGATION RECOMPOSITION: navigate -> "+screenIdentifier.URI)
        navigateToScreen(screenIdentifier)
        localNavigationState.value = localNavigationState.value.copy(
            path = (localNavigationState.value.path + screenIdentifier).toMutableList(),
            nextBackQuitsApp = nextBackQuitsApp
        )
    }
}

fun Navigation.level1NavigationProcessor(localNavigationState: MutableState<LocalNavigationState>) : (Level1Navigation) -> Unit {
    return {
        debugLogger.log("UI NAVIGATION RECOMPOSITION: navigate level 1 -> "+it.screenIdentifier.URI)
        selectLevel1Navigation(it.screenIdentifier)
        localNavigationState.value = LocalNavigationState(
            level1ScreenIdentifier = it.screenIdentifier,
            path = getPath(it.screenIdentifier),
            nextBackQuitsApp = nextBackQuitsApp
        )
    }
}
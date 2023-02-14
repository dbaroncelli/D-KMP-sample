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



@Composable
fun Navigation.Router() {

    val screenUIsStateHolder = rememberSaveableStateHolder()
    val localNavigationState = remember { mutableStateOf( navigationState ) }

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

fun Navigation.navigationProcessor(localNavigationState: MutableState<NavigationState>) : (Screen, ScreenParams?) -> Unit {
    return { screen, screenParams ->
        val screenIdentifier = ScreenIdentifier.get(screen, screenParams)
        navigateToScreen(screenIdentifier) // change navigationState
        localNavigationState.value = navigationState
    }
}

fun Navigation.level1NavigationProcessor(localNavigationState: MutableState<NavigationState>) : (Level1Navigation) -> Unit {
    return {
        selectLevel1Navigation(it.screenIdentifier) // change navigationState
        localNavigationState.value = navigationState
    }
}
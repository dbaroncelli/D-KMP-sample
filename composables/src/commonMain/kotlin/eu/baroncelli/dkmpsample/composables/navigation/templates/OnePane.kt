package eu.baroncelli.dkmpsample.composables.navigation.templates

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.saveable.SaveableStateHolder
import eu.baroncelli.dkmpsample.composables.navigation.*
import eu.baroncelli.dkmpsample.composables.navigation.bars.Level1BottomBar
import eu.baroncelli.dkmpsample.composables.navigation.bars.TopBar
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation

@Composable
fun Navigation.OnePane(
    saveableStateHolder: SaveableStateHolder,
    localNavigationState: MutableState<LocalNavigationState>
) {
    val screenIdentifier = localNavigationState.value.topScreenIdentifier
    val title = getTitle(screenIdentifier)
    Scaffold(
        topBar = { TopBar(title) },
        content = {
            saveableStateHolder.SaveableStateProvider(screenIdentifier.URI) {
                ScreenPicker(screenIdentifier, navigationProcessor(localNavigationState))
            }
        },
        bottomBar = { if (screenIdentifier.screen.navigationLevel == 1) Level1BottomBar(screenIdentifier, level1NavigationProcessor(localNavigationState)) }
    )
}
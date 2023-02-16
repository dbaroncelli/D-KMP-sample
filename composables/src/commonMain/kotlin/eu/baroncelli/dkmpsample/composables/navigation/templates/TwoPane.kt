package eu.baroncelli.dkmpsample.composables.navigation.templates

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.baroncelli.dkmpsample.composables.navigation.*
import eu.baroncelli.dkmpsample.composables.navigation.bars.Level1NavigationRail
import eu.baroncelli.dkmpsample.composables.navigation.bars.TopBar
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.NavigationState
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger

@Composable
fun Navigation.TwoPane(
    saveableStateHolder: SaveableStateHolder,
    localNavigationState: MutableState<NavigationState>
) {
    val title = getTitle(localNavigationState.value.topScreenIdentifier)
    val masterScreenIdentifier = twoPaneMasterScreen(localNavigationState)
    val detailScreenIdentifier = twoPaneDetailScreen(localNavigationState)
    Scaffold(
        topBar = { TopBar(title) },
        content = {
            Row {
                Column(Modifier
                    .fillMaxHeight()
                    .width(80.dp)) {
                        Level1NavigationRail(masterScreenIdentifier, level1NavigationProcessor(localNavigationState))
                }
                Column(Modifier
                    .weight(0.4f)) {
                        saveableStateHolder.SaveableStateProvider(masterScreenIdentifier.URI) {
                            ScreenPicker(masterScreenIdentifier, navigationProcessor(localNavigationState))
                        }
                }
                Column(Modifier
                    .weight(0.6f)
                    .padding(20.dp)) {
                        if (detailScreenIdentifier == null) {
                            TwoPaneDefaultDetail(masterScreenIdentifier)
                        } else {
                            saveableStateHolder.SaveableStateProvider(detailScreenIdentifier.URI) {
                                ScreenPicker(detailScreenIdentifier, navigationProcessor(localNavigationState))
                            }
                        }
                }
            }
        }
    )
}



fun Navigation.twoPaneMasterScreen(navState: MutableState<NavigationState>) : ScreenIdentifier {
    return navState.value.currentLevel1ScreenIdentifier
}

fun Navigation.twoPaneDetailScreen(navState: MutableState<NavigationState>) : ScreenIdentifier? {
    if (navState.value.topScreenIdentifier.screen.navigationLevel > 1) {
        return navState.value.topScreenIdentifier
    } else {
        return null
    }
}
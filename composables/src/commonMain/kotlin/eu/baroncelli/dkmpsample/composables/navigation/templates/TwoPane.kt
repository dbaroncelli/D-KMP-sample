package eu.baroncelli.dkmpsample.composables.navigation.templates

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.baroncelli.dkmpsample.composables.navigation.ScreenPicker
import eu.baroncelli.dkmpsample.composables.navigation.TwoPaneDefaultDetail
import eu.baroncelli.dkmpsample.composables.navigation.bars.Level1NavigationRail
import eu.baroncelli.dkmpsample.composables.navigation.bars.TopBar
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation

@Composable
fun Navigation.TwoPane(
    saveableStateHolder: SaveableStateHolder
) {
    val navigationLevelsMap = getNavigationLevelsMap(currentLevel1ScreenIdentifier)!!
    Scaffold(
        topBar = { TopBar(getTitle(currentScreenIdentifier)) },
        content = {
            Row {
                Column(Modifier
                    .fillMaxHeight()
                    .width(80.dp)) {
                        Level1NavigationRail(selectedTab = navigationLevelsMap[1]!!)
                }
                Column(Modifier
                    .weight(0.4f)) {
                        saveableStateHolder.SaveableStateProvider(navigationLevelsMap[1]!!.URI) {
                            ScreenPicker(navigationLevelsMap[1]!!)
                        }
                }
                Column(Modifier
                    .weight(0.6f)
                    .padding(20.dp)) {
                        if (navigationLevelsMap[2] == null) {
                            TwoPaneDefaultDetail(navigationLevelsMap[1]!!)
                        } else {
                            saveableStateHolder.SaveableStateProvider(navigationLevelsMap[2]!!.URI) {
                                ScreenPicker(navigationLevelsMap[2]!!)
                            }
                        }
                }
            }
        }
    )
}
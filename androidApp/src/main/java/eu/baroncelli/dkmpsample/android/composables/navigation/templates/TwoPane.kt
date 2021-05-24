package eu.baroncelli.dkmpsample.android.composables.navigation.templates

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.baroncelli.dkmpsample.android.composables.navigation.ScreenPicker
import eu.baroncelli.dkmpsample.android.composables.navigation.TwoPaneDefaultDetail
import eu.baroncelli.dkmpsample.android.composables.screens.countrieslist.Level1BottomBar
import eu.baroncelli.dkmpsample.shared.viewmodel.Events
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider

@Composable
fun Navigation.TwoPane(
    saveableStateHolder: SaveableStateHolder
) {
    Scaffold(
        topBar = { TopBar(title) },
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
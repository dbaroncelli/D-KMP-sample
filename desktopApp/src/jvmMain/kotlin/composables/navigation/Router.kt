package eu.baroncelli.dkmpsample.desktop.composables.navigation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.unit.dp
import eu.baroncelli.dkmpsample.desktop.composables.navigation.templates.OnePane
import eu.baroncelli.dkmpsample.desktop.composables.navigation.templates.TwoPane
import eu.baroncelli.dkmpsample.shared.viewmodel.*

@Composable
fun Navigation.Router() {

    val screenUIsStateHolder = rememberSaveableStateHolder()

    val twopaneWidthThreshold = 1000.dp
    BoxWithConstraints() {
        if (maxWidth < maxHeight || maxWidth<twopaneWidthThreshold) {
            OnePane(screenUIsStateHolder)
        } else {
            TwoPane(screenUIsStateHolder)
        }
    }

    screenStatesToRemove.forEach {
        screenUIsStateHolder.removeState(it.URI)
        println("D-KMP: removed UI screen "+it.URI)
    }

    /*
    if (!only1ScreenInBackstack) {
        BackHandler { // catching the back button to update the DKMPViewModel
            exitScreen()
        }
    }
    */

}
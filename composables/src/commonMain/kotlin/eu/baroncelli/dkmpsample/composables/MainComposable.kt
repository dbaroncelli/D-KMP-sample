package eu.baroncelli.dkmpsample.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import eu.baroncelli.dkmpsample.composables.navigation.Router
import eu.baroncelli.dkmpsample.shared.viewmodel.DKMPViewModel

@Composable
fun MainComposable(model: DKMPViewModel) {
    val appState by model.stateFlow.collectAsState()
    println("D-KMP-SAMPLE: APP STATE RECOMPOSITION: index #"+appState.recompositionIndex.toString())
    val dkmpNav = appState.getNavigation(model)
    dkmpNav.Router()
}


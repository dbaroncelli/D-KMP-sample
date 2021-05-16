package eu.baroncelli.dkmpsample.android.composables.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import eu.baroncelli.dkmpsample.shared.viewmodel.DKMPViewModel

@Composable
fun MainComposable(model: DKMPViewModel) {
    val appState by model.stateFlow.collectAsState()
    Log.d("D-KMP-SAMPLE","recomposition Index: "+appState.recompositionIndex.toString())
    val dkmpNav = appState.getNavigation(model)

    dkmpNav.Router(model.stateProvider, model.events)

}


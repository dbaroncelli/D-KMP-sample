package eu.baroncelli.dkmpsample.android

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import eu.baroncelli.dkmpsample.shared.viewmodel.DKMPViewModel

@Composable
fun MainComposable(model: DKMPViewModel) {

    val appState by model.stateFlow.collectAsState()
    Log.d("D-KMP-SAMPLE","recomposition Index: "+appState.recompositionIndex.toString())
    val stateProviders = appState.getStateProviders(model)
    val events = model.events

    val navController = rememberNavController()
    val dkmpNav = remember(model, navController) { DKMPNavigation(model, navController) }

    NavHost(navController, startDestination = dkmpNav.getStartDestination()) {
        dkmpNav.navGraphBuilder = this
        dkmpNav.getComposables(stateProviders, events)
    }

}
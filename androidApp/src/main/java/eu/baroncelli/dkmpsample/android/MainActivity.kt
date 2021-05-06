package eu.baroncelli.dkmpsample.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import eu.baroncelli.dkmpsample.android.styling.MyComposeTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model = (application as DKMPApp).model
        setContent {
            MyComposeTheme {

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
        }
    }

}
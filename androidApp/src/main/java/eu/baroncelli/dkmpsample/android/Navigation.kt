package eu.baroncelli.dkmpsample.android

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import eu.baroncelli.dkmpsample.android.screens.countrydetail.CountryDetailScreen
import eu.baroncelli.dkmpsample.android.screens.countrieslist.CountriesListScreen
import eu.baroncelli.dkmpsample.shared.viewmodel.KMPViewModel
import androidx.compose.runtime.getValue
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.getCountryDetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.getCountriesListState

@Composable
fun Navigation(model: KMPViewModel) {

    val events = model.events
    val appState by model.stateFlow.collectAsState()
    Log.d("D-KMP","recomposition Index: "+appState.recompositionIndex.toString())
    val stateProvider = appState.getStateProvider(model)


    val navController = rememberNavController()

    NavHost(navController, startDestination = "countrieslist") {
        composable("countrieslist") {
            CountriesListScreen(
                countriesListState = stateProvider.getCountriesListState(),
                events = events,
                onListItemClick = { navController.navigate("country/$it") },
            )
        }
        composable("country/{item}") { backStackEntry ->
            val item = backStackEntry.arguments?.getString("item")!!
            CountryDetailScreen(
                countryDetailState = stateProvider.getCountryDetailState(item)
            )
        }
    }

}
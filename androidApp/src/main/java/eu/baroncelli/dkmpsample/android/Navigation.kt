package eu.baroncelli.dkmpsample.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import eu.baroncelli.dkmpsample.android.detail.DetailScreen
import eu.baroncelli.dkmpsample.android.master.MasterScreen
import eu.baroncelli.dkmpsample.shared.viewmodel.KMPViewModel
import androidx.compose.runtime.getValue
import eu.baroncelli.dkmpsample.shared.viewmodel.detail.getDetail
import eu.baroncelli.dkmpsample.shared.viewmodel.master.getMaster

@Composable
fun Navigation(model: KMPViewModel) {

    val events = model.events
    val stateObject by model.stateFlow.collectAsState()
    val stateProvider = stateObject.getStateProvider(model)


    val navController = rememberNavController()

    NavHost(navController, startDestination = "master") {
        composable("master") {
            MasterScreen(
                masterState = stateProvider.getMaster(),
                events = events,
                onListItemClick = { navController.navigate("detail/$it") },
            )
        }
        composable("detail/{item}") { backStackEntry ->
            val item = backStackEntry.arguments?.getString("item")!!
            DetailScreen(
                detailState = stateProvider.getDetail(item)
            )
        }
    }

}
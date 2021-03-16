package eu.baroncelli.dkmpsample.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.*
import androidx.navigation.compose.composable
import eu.baroncelli.dkmpsample.android.detail.DetailScreen
import eu.baroncelli.dkmpsample.android.master.MasterScreen
import eu.baroncelli.dkmpsample.shared.viewmodel.KMPViewModel
import eu.baroncelli.dkmpsample.shared.viewmodel.events.loadDetailItem

@Composable
fun Navigation(model: KMPViewModel) {

    val appState by model.stateFlow.collectAsState()

    val navController = rememberNavController()

    NavHost(navController, startDestination = "master") {
        composable("master") {
            MasterScreen(
                model = model,
                masterState = appState.masterState,
                onListItemClick = {
                    model.loadDetailItem(it)
                    navController.navigate("detail/$it")
                }
            )
        }
        composable("detail/{item}") { backStackEntry ->
            val item = backStackEntry.arguments?.getString("item")!!
            DetailScreen(
                detailName = item,
                detailState = appState.detailState
            )
        }
    }

}
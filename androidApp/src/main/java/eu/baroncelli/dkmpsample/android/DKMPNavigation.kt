package eu.baroncelli.dkmpsample.android

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import eu.baroncelli.dkmpsample.shared.viewmodel.DKMPViewModel
import eu.baroncelli.dkmpsample.shared.viewmodel.Screen

class DKMPNavigation(private val model: DKMPViewModel, private val navController : NavHostController) {

    lateinit var navGraphBuilder: NavGraphBuilder

    fun dkmpComposable(
        screen: Screen,
        content: @Composable (NavBackStackEntry) -> Unit
    ) {
        var routeId = screen.route
        if (screen.multipleInstances) {
            routeId += "/{instanceId}"
        }

        navGraphBuilder.composable(routeId, emptyList(), emptyList()) { navBackStackEntry ->
            if (navController.currentDestination?.id != navController.graph.startDestination) { // only if it's not the start screen
                // Note: if it's the start screen, the back button is managed directly by the Activity, which closes the app
                BackHandler { // catching the back button to update the DKMPModel
                    back()
                }
            }
            content(navBackStackEntry)
        }

    }



    fun getStartDestination() : String {
        return model.getStartScreen().route
    }


    private fun getCurrentRouteId() : String {
        var currentRouteId = navController.currentBackStackEntry?.arguments?.getString(KEY_ROUTE)
        if (currentRouteId == null) {
            return ""
        }
        val screenInstanceId = getScreenInstanceId()
        if (screenInstanceId != null) {
            currentRouteId = currentRouteId.replace("{instanceId}", screenInstanceId)
        }
        return currentRouteId
    }

    fun getScreenInstanceId() : String? {
        return navController.currentBackStackEntry?.arguments?.getString("instanceId")
    }


    fun navigate(screen : Screen, instanceId : String?) {
        var routeId = screen.route
        if (instanceId != null) {
            routeId += "/$instanceId"
        }
        navController.navigate(routeId)
    }

    private fun back() {
        val oldRouteId = getCurrentRouteId()
        navController.popBackStack()
        model.exitScreen(oldRouteId, getCurrentRouteId())
    }


}
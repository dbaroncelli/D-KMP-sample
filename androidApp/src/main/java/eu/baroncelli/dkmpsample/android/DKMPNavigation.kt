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

class DKMPNavigation(val model: DKMPViewModel, val navController : NavHostController) {

    lateinit var navGraphBuilder: NavGraphBuilder

    fun dkmpComposable(
        screen: Screen,
        multipleInstances : Boolean = false,
        content: @Composable (NavBackStackEntry) -> Unit
    ) {
        var route = screen.route
        if (multipleInstances) {
            route += "/{instanceId}"
        }

        navGraphBuilder.composable(route, emptyList(), emptyList()) { navBackStackEntry ->
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


    fun getCurrentRouteId() : String {
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
        val screenID = navController.currentBackStackEntry?.arguments?.getString("instanceId")
        return screenID
    }

    fun navigate(screen : Screen, instanceId : String?) {
        var routeId = screen.route
        if (instanceId != null) {
            routeId += "/"+instanceId
        }
        navController.navigate(routeId)
    }

    fun back() {
        val oldRouteId = getCurrentRouteId()
        navController.popBackStack()
        model.exitScreen(oldRouteId, getCurrentRouteId())
    }


}
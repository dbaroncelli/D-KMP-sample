package eu.baroncelli.dkmpsample.composables.navigation.bars

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Level1Navigation


// this is the left vertical navigation bar for 2-Pane visualization
// (used by bigger devices in landscape mode)

@Composable
fun Navigation.Level1NavigationRail(
    selectedTab: ScreenIdentifier,
    navigateByLevel1Menu: (Level1Navigation) -> Unit
) {
    NavigationRail(content = {
        Spacer(Modifier.weight(1f))
        NavigationRailItem(
            icon = { Icon(Icons.Default.Menu, "ALL") },
            label = { Text("All Countries", fontSize = 13.sp) },
            selected = selectedTab.URI == Level1Navigation.AllCountries.screenIdentifier.URI,
            onClick = { navigateByLevel1Menu(Level1Navigation.AllCountries) }
        )
        NavigationRailItem(
            icon = { Icon(Icons.Default.Star, "FAVORITES") },
            label = { Text("Favourites", fontSize = 13.sp) },
            selected = selectedTab.URI == Level1Navigation.FavoriteCountries.screenIdentifier.URI,
            onClick = { navigateByLevel1Menu(Level1Navigation.FavoriteCountries) }
        )
        Spacer(Modifier.weight(1f))
    }, modifier = Modifier.fillMaxSize(), containerColor = MaterialTheme.colorScheme.primaryContainer)
}

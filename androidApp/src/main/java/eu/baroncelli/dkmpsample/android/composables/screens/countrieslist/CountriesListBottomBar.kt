package eu.baroncelli.dkmpsample.android.composables.screens.countrieslist

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Level1Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListType


@Composable
fun CountriesListBottomBar(
    selectedTab: ScreenIdentifier,
    onItemClick: (Level1Navigation) -> Unit,
) {
    BottomAppBar(content = {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Menu, "ALL") },
            label = { Text("All", fontSize = 13.sp) },
            selected = selectedTab.URI == Level1Navigation.AllCountries.screenIdentifier.URI,
            onClick = { onItemClick(Level1Navigation.AllCountries) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Star, "FAVORITES") },
            label = { Text("Favourites", fontSize = 13.sp) },
            selected = selectedTab.URI == Level1Navigation.FavoriteCountries.screenIdentifier.URI,
            onClick = { onItemClick(Level1Navigation.FavoriteCountries) }
        )
    })
}
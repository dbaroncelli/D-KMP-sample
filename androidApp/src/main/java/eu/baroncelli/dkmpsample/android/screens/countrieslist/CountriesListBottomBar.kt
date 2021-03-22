package eu.baroncelli.dkmpsample.android.screens.countrieslist

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.MenuItem


@Composable
fun CountriesListBottomBar(selectedItem : MenuItem, onItemClick: (MenuItem) -> Unit) {
    BottomAppBar(content = {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Menu, "ALL") },
            label = { Text("All", fontSize = 13.sp) },
            selected = selectedItem == MenuItem.ALL,
            onClick = { onItemClick(MenuItem.ALL) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Star, "FAVORITES") },
            label = { Text("Favourites", fontSize = 13.sp) },
            selected = selectedItem == MenuItem.FAVORITES,
            onClick = { onItemClick(MenuItem.FAVORITES) }
        )
    })
}
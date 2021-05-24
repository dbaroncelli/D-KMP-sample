package eu.baroncelli.dkmpsample.android.composables.navigation.templates

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Level1Navigation

@Composable
fun Navigation.Level1NavigationRail(
    selectedTab: ScreenIdentifier,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.primary)
    ) {
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
    }
}



@Composable
fun ColumnScope.NavigationRailItem(icon : @Composable () -> Unit , label :  @Composable () -> Unit, selected : Boolean, onClick : () -> Unit) {
    CompositionLocalProvider(
        LocalContentColor provides if (selected) MaterialTheme.colors.background else  MaterialTheme.colors.primaryVariant
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Column(Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable { onClick() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                icon()
                label()
            }
        }
    }
}

package eu.baroncelli.dkmpsample.android.composables.screens.countrieslist


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.baroncelli.dkmpsample.android.composables.screens.LoadingScreen
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Level1Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen.*
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListState

@Composable
fun CountriesListScreen(
    countriesListState: CountriesListState,
    onMenuItemClick: (Level1Navigation) -> Unit,
    onListItemClick: (String) -> Unit,
    onFavoriteIconClick : (String) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "D-KMP sample", fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            })
        },
        content = { paddingValues ->
            if (countriesListState.isLoading) {
                LoadingScreen()
            } else {
                if (countriesListState.countriesListItems.isEmpty()) {
                    Text(text = "empty list", style = MaterialTheme.typography.body1, modifier = Modifier.padding(top=30.dp).fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 18.sp)
                } else {
                    LazyColumn(contentPadding = paddingValues) {
                        stickyHeader {
                            CountriesListHeader()
                        }
                        items(items = countriesListState.countriesListItems, itemContent = { item ->
                            CountriesListRow(
                                item = item,
                                favorite = countriesListState.favoriteCountries.containsKey(item.name),
                                onItemClick = { onListItemClick(item.name) },
                                onFavoriteIconClick = { onFavoriteIconClick(item.name) })
                        })
                    }
                }
            }
        },
        bottomBar = {
            CountriesListBottomBar(selectedTab = ScreenIdentifier.get(CountriesList, countriesListState.params), onItemClick = { onMenuItemClick(it) })
        }
    )
}
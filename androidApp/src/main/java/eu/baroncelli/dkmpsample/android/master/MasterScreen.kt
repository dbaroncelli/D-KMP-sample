package eu.baroncelli.dkmpsample.android.master


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
import eu.baroncelli.dkmpsample.android.LoadingScreen
import eu.baroncelli.dkmpsample.shared.viewmodel.Events
import eu.baroncelli.dkmpsample.shared.viewmodel.master.MasterState
import eu.baroncelli.dkmpsample.shared.viewmodel.master.selectFavorite
import eu.baroncelli.dkmpsample.shared.viewmodel.master.selectMenuItem

@Composable
fun MasterScreen(masterState: MasterState, events : Events, onListItemClick: (String) -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "D-KMP sample", fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            })
        },
        content = { paddingValues ->
            if (masterState.isLoading) {
                LoadingScreen()
            } else {
                if (masterState.countriesList.isEmpty()) {
                    Text(text = "empty list", style = MaterialTheme.typography.body1, modifier = Modifier.padding(top=30.dp).fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 18.sp)
                } else {
                    LazyColumn(contentPadding = paddingValues) {
                        stickyHeader {
                            MasterListHeader()
                        }
                        items(items = masterState.countriesList, itemContent = { countryRow ->
                            MasterListItem(
                                item = countryRow,
                                favorite = masterState.favoriteCountries.containsKey(countryRow.name),
                                onItemClick = { onListItemClick(countryRow.name) },
                                onFavoriteIconClick = { events.selectFavorite(countryRow.name) })
                        })
                    }
                }
            }
        },
        bottomBar = {
            MasterBottomBar(selectedItem = masterState.selectedMenuItem, onItemClick = { events.selectMenuItem(it) })
        }
    )
}
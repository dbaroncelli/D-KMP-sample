package eu.baroncelli.dkmpsample.composables.screens.countrieslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListItem

@Composable
fun CountriesListRow(
    item: CountriesListItem,
    favorite : Boolean,
    onItemClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth().clickable(onClick = onItemClick).height(50.dp).padding(start=10.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f).padding(end = 10.dp)) {
            Text(text = item.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        }
        Column(modifier = Modifier.width(70.dp), horizontalAlignment = Alignment.End) {
            Text(text = item.firstDosesPerc, style = MaterialTheme.typography.bodyLarge)
        }
        Column(modifier = Modifier.width(70.dp), horizontalAlignment = Alignment.End) {
            Text(text = item.fullyVaccinatedPerc, style = MaterialTheme.typography.bodyLarge)
        }
        Column(modifier = Modifier.fillMaxHeight().width(100.dp).clickable(onClick = onFavoriteIconClick), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            if (favorite) {
                Icon(Icons.Default.Star, contentDescription = "favorite", tint = Color.Magenta)
            } else {
                Icon(Icons.Default.Star, contentDescription = "not a favorite", tint = Color.LightGray)
            }
        }
    }
    Divider()
}

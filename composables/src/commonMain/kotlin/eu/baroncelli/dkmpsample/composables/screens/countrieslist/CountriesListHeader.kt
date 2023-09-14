package eu.baroncelli.dkmpsample.composables.screens.countrieslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CountriesListHeader() {
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp).background(Color.LightGray).padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "country", fontSize = 16.sp)
        }
        Column(modifier = Modifier.width(70.dp), horizontalAlignment = Alignment.End) {
            Text(text = "first\ndose", fontSize = 15.sp, textAlign = TextAlign.Right)
        }
        Column(modifier = Modifier.width(70.dp), horizontalAlignment = Alignment.End) {
            Text(text = "fully\nvax'd", fontSize = 15.sp, textAlign = TextAlign.Right)
        }
        Column(modifier = Modifier.width(100.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "favorite?", fontSize = 15.sp, textAlign = TextAlign.Center)
        }
    }
}
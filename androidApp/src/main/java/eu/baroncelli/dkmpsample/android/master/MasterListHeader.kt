package eu.baroncelli.dkmpsample.android.master

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MasterListHeader() {
    Row(modifier = Modifier.fillMaxWidth().height(50.dp).background(Color.LightGray).padding(start=10.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Row { Text(text = "country", fontSize = 16.sp) }
        }
        Column(modifier = Modifier.width(70.dp), horizontalAlignment = Alignment.End) {
            Row { Text(text = "first\ndose", fontSize = 15.sp, textAlign = TextAlign.Right) }
        }
        Column(modifier = Modifier.width(70.dp), horizontalAlignment = Alignment.End) {
            Row { Text(text = "fully\nvax'd", fontSize = 15.sp, textAlign = TextAlign.Right) }
        }
        Column(modifier = Modifier.width(100.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row { Text(text = "favorite?", fontSize = 15.sp, textAlign = TextAlign.Center) }
        }
    }
}
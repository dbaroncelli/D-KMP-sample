package eu.baroncelli.dkmpsample.android.composables.screens

import androidx.compose.material.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoadingScreen() {
    Column(modifier = Modifier.fillMaxWidth() then Modifier.padding(top=120.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "loading...", fontSize = 18.sp)
        Spacer(modifier = Modifier.size(40.dp))
        CircularProgressIndicator(color = Color.LightGray)
    }
}
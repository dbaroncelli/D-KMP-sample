package eu.baroncelli.dkmpsample.composables.navigation.bars

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import eu.baroncelli.dkmpsample.composables.styling.Purple700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title : String
){
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary)
    )
}
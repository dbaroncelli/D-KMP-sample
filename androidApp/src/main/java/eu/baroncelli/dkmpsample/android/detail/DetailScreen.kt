package eu.baroncelli.dkmpsample.android.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import eu.baroncelli.dkmpsample.android.LoadingScreen
import eu.baroncelli.dkmpsample.shared.viewmodel.appstate.detail.DetailState

@Composable
fun DetailScreen(detailName : String, detailState: DetailState) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row (verticalAlignment = Alignment.Bottom) {
                    Text(text = "country: ", fontSize = 20.sp)
                    Text(text = detailName, fontSize = 18.sp, fontStyle = FontStyle.Italic)
                }
            })
        },
        content = { paddingValues ->
            if (detailState.isLoading) {
                LoadingScreen()
            } else {
                DetailContent(data = detailState.countryInfo, paddingValues = paddingValues)
            }
        },
    )
}
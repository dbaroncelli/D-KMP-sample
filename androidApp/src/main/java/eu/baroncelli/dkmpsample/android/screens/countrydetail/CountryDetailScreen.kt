package eu.baroncelli.dkmpsample.android.screens.countrydetail

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import eu.baroncelli.dkmpsample.android.LoadingScreen
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailState

@Composable
fun CountryDetailScreen(
    countryDetailState: CountryDetailState,
) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row (verticalAlignment = Alignment.Bottom) {
                    Text(text = "country: ", fontSize = 20.sp)
                    Text(text = countryDetailState.params.countryName, fontSize = 18.sp, fontStyle = FontStyle.Italic)
                }
            })
        },
        content = { paddingValues ->
            if (countryDetailState.isLoading) {
                LoadingScreen()
            } else {
                CountryDetailContent(data = countryDetailState.countryInfo, paddingValues = paddingValues)
            }
        },
    )
}
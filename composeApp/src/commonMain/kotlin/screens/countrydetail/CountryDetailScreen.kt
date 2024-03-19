package eu.baroncelli.dkmpsample.screens.countrydetail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import eu.baroncelli.dkmpsample.screens.LoadingScreen
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailState

@Composable
fun CountryDetailScreen(
    countryDetailState: CountryDetailState,
) {

    if (countryDetailState.isLoading) {

        LoadingScreen()

    } else {
        val data = countryDetailState.countryInfo
        Column(modifier = Modifier.padding(10.dp)) {

            DataElement("total population", data.population)
            DataElement("   with first dose", data.firstDoses, data.firstDosesPerc)
            DataElement("   fully vaccinated", data.fullyVaccinated, data.fullyVaccinatedPerc)

            Spacer(modifier = Modifier.size(24.dp))

            if (data.vaccinesList!=null) {
                Text(text = "Vaccines:", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                for (vaccine in data.vaccinesList!!) {
                    Text(text = "   ‣ $vaccine", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

    }
}



@Composable
fun DataElement(label : String, value : String = "", percentage : String = "") {
    Row {
        Text(text = "$label: ", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
        if (percentage!="") {
            Text(text = " ($percentage)", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
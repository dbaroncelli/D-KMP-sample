package eu.baroncelli.dkmpsample.composables.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import eu.baroncelli.dkmpsample.composables.screens.countrieslist.CountriesListScreen
import eu.baroncelli.dkmpsample.composables.screens.countrieslist.CountriesListTwoPaneDefaultDetail
import eu.baroncelli.dkmpsample.composables.screens.countrydetail.CountryDetailScreen
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenParams
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen.CountriesList
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen.CountryDetail
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.selectFavorite
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailParams
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailState


@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
    navigate: (Screen, ScreenParams?) -> Unit
) {

    when (screenIdentifier.screen) {

        CountriesList -> {
            val state by stateProvider.get<CountriesListState>(screenIdentifier).collectAsState()
            CountriesListScreen(
                countriesListState = state,
                onListItemClick = { navigate(CountryDetail, CountryDetailParams(countryName = it)) },
                onFavoriteIconClick = { events.selectFavorite(countryName = it) },
            )
        }

        CountryDetail -> {
            val state by stateProvider.get<CountryDetailState>(screenIdentifier).collectAsState()
            CountryDetailScreen(
                countryDetailState = state
            )
        }

        else ->
            return

    }

}


@Composable
fun Navigation.TwoPaneDefaultDetail(
    screenIdentifier: ScreenIdentifier
) {

    when (screenIdentifier.screen) {

        CountriesList ->
            CountriesListTwoPaneDefaultDetail()

        else -> Box {}
    }

}
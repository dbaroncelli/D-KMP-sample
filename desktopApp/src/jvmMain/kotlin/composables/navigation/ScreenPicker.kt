package eu.baroncelli.dkmpsample.desktop.composables.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import eu.baroncelli.dkmpsample.desktop.composables.screens.countrydetail.CountryDetailScreen
import eu.baroncelli.dkmpsample.desktop.composables.screens.countrieslist.CountriesListScreen
import eu.baroncelli.dkmpsample.desktop.composables.screens.countrieslist.CountriesListTwoPaneDefaultDetail
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen.*
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.selectFavorite
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailParams


@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier
) {

    when (screenIdentifier.screen) {

        CountriesList ->
            CountriesListScreen(
                countriesListState = stateProvider.get(screenIdentifier),
                onListItemClick = { navigate(CountryDetail, CountryDetailParams(countryName = it)) },
                onFavoriteIconClick = { events.selectFavorite(countryName = it) },
            )

        CountryDetail ->
            CountryDetailScreen(
                countryDetailState = stateProvider.get(screenIdentifier)
            )

    }

}



@Composable
fun Navigation.TwoPaneDefaultDetail(
    screenIdentifier: ScreenIdentifier
) {

    when (screenIdentifier.screen) {

        CountriesList ->
            CountriesListTwoPaneDefaultDetail()

        else -> Box{}
    }

}
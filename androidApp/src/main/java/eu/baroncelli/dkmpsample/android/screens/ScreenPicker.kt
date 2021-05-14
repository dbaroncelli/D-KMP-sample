package eu.baroncelli.dkmpsample.android

import androidx.compose.runtime.Composable
import eu.baroncelli.dkmpsample.android.screens.countrydetail.CountryDetailScreen
import eu.baroncelli.dkmpsample.android.screens.countrieslist.CountriesListScreen
import eu.baroncelli.dkmpsample.shared.viewmodel.Events
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.StateProviders
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListParams
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.selectFavorite
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailParams


@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
    stateProviders: StateProviders,
    events: Events,
) {

    when (screenIdentifier.screen) {

        Screen.CountriesList ->
            CountriesListScreen(
                countriesListState = stateProviders.get(screenIdentifier),
                onMenuItemClick = { navigate(Screen.CountriesList, CountriesListParams(listType = it)) },
                onListItemClick = { navigate(Screen.CountryDetail, CountryDetailParams(countryName = it)) },
                onFavoriteIconClick = { events.selectFavorite(country = it) },
            )

        Screen.CountryDetail ->
            CountryDetailScreen(
                countryDetailState = stateProviders.get(screenIdentifier)
            )
    }

}
package eu.baroncelli.dkmpsample.android.composables.navigation

import androidx.compose.runtime.Composable
import eu.baroncelli.dkmpsample.android.composables.screens.countrydetail.CountryDetailScreen
import eu.baroncelli.dkmpsample.android.composables.screens.countrieslist.CountriesListScreen
import eu.baroncelli.dkmpsample.shared.viewmodel.Events
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenIdentifier
import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.selectFavorite
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailParams


@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
    stateProvider: StateProvider,
    events: Events,
) {
    when (screenIdentifier.screen) {

        Screen.CountriesList ->
            CountriesListScreen(
                countriesListState = stateProvider.get(screenIdentifier),
                onMenuItemClick = { navigateByLevel1Menu(level1NavigationItem = it) },
                onListItemClick = { navigate(Screen.CountryDetail, CountryDetailParams(countryName = it)) },
                onFavoriteIconClick = { events.selectFavorite(countryName = it) },
            )

        Screen.CountryDetail ->
            CountryDetailScreen(
                countryDetailState = stateProvider.get(screenIdentifier)
            )
    }

}
package eu.baroncelli.dkmpsample.shared.eu.baroncelli.dkmpsample.shared.viewmodel.statereducers

import com.russhwolf.settings.MockSettings
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.runBlockingTest
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager
import eu.baroncelli.dkmpsample.shared.viewmodel.StateReducers
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MasterReducersTests {

    @Test
    fun testDefaultTab() {
        val stateManager = StateManager()
        val stateReducers = StateReducers(stateManager, Repository(MockSettings()))
        stateManager.setScreen(CountriesListState())
        stateReducers.restoreSelectedMenuItem()
        val masterState = stateManager.getScreen(CountriesListState::class)
        assertEquals(masterState?.selectedMenuItem, MenuItem.ALL)
    }

    @Test
    fun testFavoritesTab() = runBlockingTest {
        val stateManager = StateManager()
        val stateReducers = StateReducers(stateManager, Repository(MockSettings()))
        stateManager.setScreen(CountriesListState())
        stateReducers.updateCountriesList(MenuItem.FAVORITES)
        val masterState = stateManager.getScreen(CountriesListState::class)
        assertEquals(masterState?.selectedMenuItem, MenuItem.FAVORITES)
    }

    @Test
    fun testFavoriteCountry() {
        val stateManager = StateManager()
        val stateReducers = StateReducers(stateManager, Repository(MockSettings()))
        stateManager.setScreen(CountriesListState())
        stateReducers.toggleFavorite("Italy")
        val masterState = stateManager.getScreen(CountriesListState::class)
        assertTrue(masterState?.favoriteCountries!!.containsKey("Italy"))
    }

    @Test
    fun testCountriesListStateUpdate() {
        val stateManager = StateManager()
        stateManager.setScreen(CountriesListState())
        stateManager.updateScreen(CountriesListState::class) {
            it.copy(isLoading = false)
        }
        val masterState = stateManager.getScreen(CountriesListState::class)
        assertEquals(masterState?.isLoading, false)
    }

    @Test
    fun testCountryDetailStateUpdate() {
        val stateManager = StateManager()
        stateManager.setScreen(CountryDetailState())
        stateManager.updateScreen(CountryDetailState::class) {
            it.copy(isLoading = true)
        }
        val detailState = stateManager.getScreen(CountryDetailState::class)
        assertEquals(detailState?.isLoading, true)
    }


}


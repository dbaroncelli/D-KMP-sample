package eu.baroncelli.dkmpsample.shared.eu.baroncelli.dkmpsample.shared.viewmodel.statereducers

import com.russhwolf.settings.MockSettings
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.runBlockingTest
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MasterReducersTests {

    @Test
    fun testDefaultTab() {
        val sm = StateManager(Repository(MockSettings()))
        sm.setScreen(CountriesListState())
        sm.restoreSelectedMenuItem()
        val masterState = sm.getScreen(CountriesListState::class)
        assertEquals(masterState?.selectedMenuItem, MenuItem.ALL)
    }

    @Test
    fun testFavoritesTab() = runBlockingTest {
        val sm = StateManager(Repository(MockSettings()))
        sm.setScreen(CountriesListState())
        sm.updateCountriesList(MenuItem.FAVORITES)
        val masterState = sm.getScreen(CountriesListState::class)
        assertEquals(masterState?.selectedMenuItem, MenuItem.FAVORITES)
    }

    @Test
    fun testFavoriteCountry() {
        val sm = StateManager(Repository(MockSettings()))
        sm.setScreen(CountriesListState())
        sm.toggleFavorite("Italy")
        val masterState = sm.getScreen(CountriesListState::class)
        assertTrue(masterState?.favoriteCountries!!.containsKey("Italy"))
    }

    @Test
    fun testMasterStateUpdate() {
        val sm = StateManager(Repository(MockSettings()))
        sm.setScreen(CountriesListState())
        sm.updateScreen(CountriesListState::class) {
            it.copy(isLoading = false)
        }
        val masterState = sm.getScreen(CountriesListState::class)
        assertEquals(masterState?.isLoading, false)
    }

    @Test
    fun testDetailStateUpdate() {
        val sm = StateManager(Repository(MockSettings()))
        sm.setScreen(CountryDetailState())
        sm.updateScreen(CountryDetailState::class) {
            it.copy(isLoading = true)
        }
        val detailState = sm.getScreen(CountryDetailState::class)
        assertEquals(detailState?.isLoading, true)
    }


}


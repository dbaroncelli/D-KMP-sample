package eu.baroncelli.dkmpsample.shared.eu.baroncelli.dkmpsample.shared.viewmodel.statereducers

import com.russhwolf.settings.MockSettings
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.runBlockingTest
import eu.baroncelli.dkmpsample.shared.viewmodel.*
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.*
import kotlin.reflect.KClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MasterReducersTests {

    val stateManager = StateManager()
    val stateReducers = StateReducers(stateManager, Repository(MockSettings()))

    @Test
    fun testDefaultTab() {
        initTestState { CountriesListState(isLoading = true) }
        stateReducers.restoreSelectedMenuItem()
        val testState = getTestState(CountriesListState::class)
        assertEquals(testState?.selectedMenuItem, MenuItem.ALL)
    }

    @Test
    fun testFavoritesTab() = runBlockingTest {
        initTestState { CountriesListState(isLoading = true) }
        stateReducers.updateCountriesList(MenuItem.FAVORITES)
        val testState = getTestState(CountriesListState::class)
        assertEquals(testState?.selectedMenuItem, MenuItem.FAVORITES)
    }

    @Test
    fun testFavoriteCountry() {
        initTestState { CountriesListState(isLoading = true) }
        stateReducers.toggleFavorite("Italy")
        val testState = getTestState(CountriesListState::class)
        assertTrue(testState?.favoriteCountries!!.containsKey("Italy"))
    }

    @Test
    fun testCountriesListStateUpdate() {
        initTestState { CountriesListState(isLoading = true) }
        stateManager.updateScreen(CountriesListState::class) {
            it.copy(isLoading = false)
        }
        val testState = getTestState(CountriesListState::class)
        assertEquals(testState?.isLoading, false)
    }

    @Test
    fun testCountryDetailStateUpdate() {
        initTestState { CountryDetailState(isLoading = true) }
        stateManager.updateScreen(CountryDetailState::class) {
            it.copy(isLoading = true)
        }
        val testState = getTestState(CountryDetailState::class)
        assertEquals(testState?.isLoading, true)
    }



    inline fun <reified T:ScreenState> initTestState(initState : () -> T) {
        val screenType = getScreenType(T::class)
        stateManager.screenStatesMap[screenType] = initState()
    }

    inline fun <reified T:ScreenState> getTestState(stateClass : KClass<T>) : T? {
        val screenType = getScreenType(stateClass)
        return stateManager.screenStatesMap[screenType] as? T
    }


}


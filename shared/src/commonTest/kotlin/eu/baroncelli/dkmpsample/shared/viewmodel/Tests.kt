package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.getTestRepository
import eu.baroncelli.dkmpsample.shared.runBlockingTest
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.*
import kotlin.reflect.KClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ViewModelTests {

    val stateManager = StateManager()
    val stateReducers = StateReducers(stateManager, getTestRepository())


    @Test
    fun testFavoritesTab() = runBlockingTest {
        stateManager.initTestState { CountriesListState() }
        stateReducers.updateCountriesList(MenuItem.FAVORITES)
        val testState = stateManager.getTestState(CountriesListState::class)
        assertEquals(testState?.selectedMenuItem, MenuItem.FAVORITES)
    }

    @Test
    fun testFavoriteCountry()  = runBlockingTest {
        stateManager.initTestState { CountriesListState() }
        stateReducers.updateCountriesList(null)
        stateReducers.toggleFavorite("Italy")
        val testState = stateManager.getTestState(CountriesListState::class)
        println(testState?.favoriteCountries.toString())
        assertTrue(testState?.favoriteCountries!!.containsKey("Italy"))
    }

    @Test
    fun testCountriesListStateUpdate() {
        stateManager.initTestState { CountriesListState() }
        stateManager.updateScreen(CountriesListState::class) {
            it.copy(isLoading = false)
        }
        val testState = stateManager.getTestState(CountriesListState::class)
        assertEquals(testState?.isLoading, false)
    }

    @Test
    fun testCountryDetailStateUpdate() {
        stateManager.initTestState { CountryDetailState() }
        stateManager.updateScreen(CountryDetailState::class) {
            it.copy(isLoading = true)
        }
        val testState = stateManager.getTestState(CountryDetailState::class)
        assertEquals(testState?.isLoading, true)
    }

}


inline fun <reified T: ScreenState> StateManager.initTestState(initState : () -> T) {
    val screenType = getScreenType(T::class)
    screenStatesMap[screenType] = initState()
}

inline fun <reified T: ScreenState> StateManager.getTestState(stateClass : KClass<T>) : T? {
    val screenType = getScreenType(stateClass)
    return screenStatesMap[screenType] as? T
}
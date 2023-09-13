package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.datalayer.objects.CountryExtraData
import eu.baroncelli.dkmpsample.shared.datalayer.objects.CountryListData
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localdb.countries.setCountriesList
import eu.baroncelli.dkmpsample.shared.getTestRepository
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen.CountriesList
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen.CountryDetail
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListParams
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListType
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailParams
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryInfo
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class ViewModelTests {

    lateinit var vm: DKMPViewModel
    val navigation: Navigation
        get() = vm.navigation
    val stateProvider: StateProvider
        get() = navigation.stateProvider
    val stateManager: StateManager
        get() = navigation.stateManager


    @BeforeTest
    fun setUp() = runTest {
        vm = DKMPViewModel(getTestRepository())
    }

    @Test
    fun testCountriesListStateUpdate() = runTest {
        val screenIdentifier = ScreenIdentifier.get(CountriesList, CountriesListParams(CountriesListType.ALL))
        navigation.addScreenToBackstack(screenIdentifier)!!.join()
        stateManager.updateScreen(CountriesListState::class) {
            it.copy(favoriteCountries = mapOf("Italy" to true))
        }
        val screenState = stateProvider.getScreenState<CountriesListState>(screenIdentifier).value
        assertTrue(screenState.favoriteCountries.containsKey("Italy"))
    }

    @Test
    fun testCountryDetailStateUpdate() = runTest {
        stateManager.dataRepository.localDb.setCountriesList(
            listOf(
                CountryListData(name = "Germany")
            )
        )
        val screenIdentifier = ScreenIdentifier.get(CountryDetail, CountryDetailParams("Germany"))
        navigation.addScreenToBackstack(screenIdentifier)!!.join()
        stateManager.updateScreen(CountryDetailState::class) {
            it.copy(countryInfo = CountryInfo(_extraData = CountryExtraData(vaccines = "Pfizer, Moderna, AstraZeneca")))
        }
        val screenState = stateProvider.getScreenState<CountryDetailState>(screenIdentifier).value
        assertTrue(screenState.countryInfo.vaccinesList!!.contains("Pfizer"))
    }

}

package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.datalayer.objects.CountryExtraData
import eu.baroncelli.dkmpsample.shared.getTestRepository
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.*
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailParams
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryInfo
import kotlin.test.Test
import kotlin.test.assertTrue

class ViewModelTests {

    val vm = DKMPViewModel(getTestRepository())
    val stateProvider = vm.stateProvider
    val stateManager = vm.stateProvider.stateManager


    @Test
    fun testCountriesListStateUpdate() {
        val screenIdentifier = ScreenIdentifier(Screen.CountriesList,CountriesListParams(CountriesListType.ALL))
        stateManager.addScreen(screenIdentifier, CountriesListState(screenIdentifier.params()))
        stateManager.updateScreen(CountriesListState::class) {
            it.copy(favoriteCountries = mapOf("Italy" to true))
        }
        val screenState = stateProvider.get(screenIdentifier) as CountriesListState
        assertTrue(screenState.favoriteCountries.containsKey("Italy"))
    }

    @Test
    fun testCountryDetailStateUpdate() {
        val screenIdentifier = ScreenIdentifier(Screen.CountryDetail, CountryDetailParams("Germany"))
        stateManager.addScreen(screenIdentifier, CountryDetailState(screenIdentifier.params()))
        stateManager.updateScreen(CountryDetailState::class) {
            it.copy(countryInfo = CountryInfo(_extraData = CountryExtraData(vaccines = "Pfizer, Moderna, AstraZeneca")))
        }
        val screenState = stateProvider.get(screenIdentifier) as CountryDetailState
        assertTrue(screenState.countryInfo.vaccinesList!!.contains("Pfizer"))
    }

}

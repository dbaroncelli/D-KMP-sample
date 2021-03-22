package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListState
import kotlin.reflect.KClass

data class AppState (
    val screenStatesMap : Map<ScreenType,Any> = mapOf()
) {
    fun getStateProvider(model : KMPViewModel) : StateProvider {
        return model.stateProvider
    }

}

// here we define all the screenTypes
// the AppState keeps in memory just one screenState per screenType
// in order to support dual-pane, it makes sense to have at least a MASTER and a DETAIL
enum class ScreenType{ MASTER, DETAIL, DIALOG }

// here we list all screenState classes, defining their screenType
val stateToTypeMap = mapOf<KClass<*>,ScreenType>(
    CountriesListState::class to ScreenType.MASTER,
    CountryDetailState::class to ScreenType.DETAIL,
)
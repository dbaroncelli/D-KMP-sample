package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListState
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail.CountryDetailState
import kotlin.reflect.KClass


class StateProvider (stateManager : StateManager, events : Events) {
    val stateManager by lazy { stateManager }
    internal val events by lazy { events }
}


// here we define all the screenTypes
// the AppState keeps in memory just one screenState per screenType
// in order to support dual-pane, it makes sense to have at least a MASTER and a DETAIL
enum class ScreenType{ MASTER, DETAIL, DIALOG }

// here we list all screenState classes, defining their screenType
val stateToTypeMap = mapOf<KClass<*>,ScreenType>(
    CountriesListState::class to ScreenType.MASTER,
    CountryDetailState::class to ScreenType.DETAIL,
    // other examples:
    // ProfileState::class to ScreenType.MASTER,
    // SettingsState::class to ScreenType.DETAIL,
    // DialogState::class to ScreenType.DIALOG,
)
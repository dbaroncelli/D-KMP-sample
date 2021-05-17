package eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrydetail

import eu.baroncelli.dkmpsample.shared.datalayer.objects.CountryExtraData
import eu.baroncelli.dkmpsample.shared.datalayer.objects.CountryListData
import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenParams
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenState
import eu.baroncelli.dkmpsample.shared.viewmodel.utils.toCommaThousandString
import eu.baroncelli.dkmpsample.shared.viewmodel.utils.toPercentageString
import kotlinx.serialization.Serializable

// here is the data class defining the state for this screen

data class CountryDetailState (
    override val params: CountryDetailParams,
    val isLoading: Boolean = false,
    val countryInfo : CountryInfo = CountryInfo(),
): ScreenState


/********** property classes **********/

@Serializable // Note: ScreenParams should always be set as Serializable
data class CountryDetailParams(val countryName: String) : ScreenParams

data class CountryInfo (
    val _listData : CountryListData = CountryListData(),
    val _extraData : CountryExtraData? = CountryExtraData(),
) {
    // in the ViewModel classes, our computed properties only do UI-formatting operations
    // (the arithmetical operations, such as calculating a percentage, should happen in the DataLayer classes)
    val population = _listData.population.toCommaThousandString()
    val firstDoses = _listData.firstDoses.toCommaThousandString()
    val firstDosesPerc = _listData.firstDosesPercentageFloat.toPercentageString()
    val fullyVaccinated = _listData.fullyVaccinated.toCommaThousandString()
    val fullyVaccinatedPerc = _listData.fullyVaccinatedPercentageFloat.toPercentageString()
    val vaccinesList : List<String>? = _extraData?.vaccinesList
}

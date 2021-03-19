package eu.baroncelli.dkmpsample.shared.viewmodel.detail

import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.apis.CountryExtraData
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.apis.CountryListData
import eu.baroncelli.dkmpsample.shared.viewmodel.utils.toCommaThousandString
import eu.baroncelli.dkmpsample.shared.viewmodel.utils.toPercentageString

/********** STATE CLASS DEFINITION **********/

data class DetailState (
    val detailName : String = "",
    val countryInfo : CountryInfo = CountryInfo(),
    val isLoading : Boolean = false,
)

/********** PROPERTY CLASSES DEFINITION **********/

class CountryInfo (
    _listData : CountryListData = CountryListData(),
    _extraData : CountryExtraData? = CountryExtraData(),
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

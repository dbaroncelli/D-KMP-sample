package eu.baroncelli.dkmpsample.shared.datalayer.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryListData (
    @SerialName("n") val name : String = "",
    @SerialName("p") val population : Int = 0,
    @SerialName("fd") val firstDoses : Int = 0,
    @SerialName("fv") val fullyVaccinated : Int = 0,
) {
    // in the DataLayer classes, the computed properties don't do any UI-formatting operation
    // they just process data, including any arithmetical operation
    val firstDosesPercentageFloat : Float
        get() = calculatePercentageOfPopulation(firstDoses)
    val fullyVaccinatedPercentageFloat : Float
        get() = calculatePercentageOfPopulation(fullyVaccinated)

    private fun calculatePercentageOfPopulation(value : Int) : Float {
        if (value>0) {
            return (value.toFloat() / population.toFloat()) * 100
        }
        return 0f
    }

}
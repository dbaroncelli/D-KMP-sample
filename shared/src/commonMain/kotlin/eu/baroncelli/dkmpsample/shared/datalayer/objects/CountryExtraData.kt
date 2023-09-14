package eu.baroncelli.dkmpsample.shared.datalayer.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryExtraData(
    @SerialName("v") val vaccines: String = "",
) {
    val vaccinesList: List<String>
        get() = vaccines.split(", ")

}
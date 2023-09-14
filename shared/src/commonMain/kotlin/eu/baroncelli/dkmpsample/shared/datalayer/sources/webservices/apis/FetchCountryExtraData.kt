package eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.apis

import eu.baroncelli.dkmpsample.shared.datalayer.objects.CountryExtraData
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.ApiClient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

suspend fun ApiClient.fetchCountryExtraData(country: String): CountryExtraResponse? {
    return getResponse("/dkmpd/" + country.replace(" ", "_"))
}

@Serializable
data class CountryExtraResponse(
    @SerialName("data") val data: CountryExtraData,
    @SerialName("err") val error: String? = null,
)
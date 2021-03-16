package eu.baroncelli.dkmpsample.shared.datalayer

import com.russhwolf.settings.Settings
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localsettings.MySettings
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.ApiClient
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.apis.CountryListData
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.apis.CountryExtraData
import kotlin.native.concurrent.ThreadLocal


class Repository (val settings : Settings = Settings()) {

    @ThreadLocal
    companion object Data {
        var countriesList: List<CountryListData> = emptyList()
        var countryExtraData : MutableMap<String, CountryExtraData> = mutableMapOf()
    }

    internal val webservices by lazy { ApiClient() }
    val localSettings by lazy { MySettings(settings) }

}
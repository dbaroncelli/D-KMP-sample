package eu.baroncelli.dkmpsample.shared.datalayer

import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import eu.baroncelli.dkmpsample.shared.datalayer.objects.CountryExtraData
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localsettings.MySettings
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.ApiClient
import kotlinx.coroutines.*
import mylocal.db.LocalDb
import kotlin.native.concurrent.ThreadLocal

class Repository (val dispatcher : CoroutineDispatcher, val sqlDriver : SqlDriver, val settings : Settings = Settings()) {

    internal val localDb by lazy { LocalDb(sqlDriver) }
    internal val webservices by lazy { ApiClient() }
    val localSettings by lazy { MySettings(settings) }

    @ThreadLocal
    internal companion object RuntimeCache {
        internal var countryExtraData: MutableMap<String, CountryExtraData> = mutableMapOf()
    }

    // we run repository functions in the coroutine dispatcher specified in the Repository costructor
    // on Android we pass Dispatchers.Default
    // on iOS we pass Dispatchers.Main (for the moment, until the new Kotlin/Native memory model is ready)
    suspend fun <T> withRepoContext (block: suspend CoroutineScope.() -> T) : T {
        return withContext(dispatcher) {
            block()
        }
    }

}
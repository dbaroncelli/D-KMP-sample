package eu.baroncelli.dkmpsample.shared.datalayer

import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localsettings.MySettings
import eu.baroncelli.dkmpsample.shared.datalayer.sources.runtimecache.CacheObjects
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.ApiClient
import kotlinx.coroutines.*
import mylocal.db.LocalDb

class Repository (val sqlDriver : SqlDriver, val settings : Settings = Settings(), val useDefaultDispatcher : Boolean = true) {

    internal val webservices by lazy { ApiClient() }
    internal val localDb by lazy { LocalDb(sqlDriver) }
    internal val localSettings by lazy { MySettings(settings) }
    internal val runtimeCache get() = CacheObjects

    // we run each repository function on a Dispatchers.Default coroutine
    // we pass useDefaultDispatcher=false just for the TestRepository instance
    suspend fun <T> withRepoContext (block: suspend () -> T) : T {
        return if (useDefaultDispatcher) {
            withContext(Dispatchers.Default) {
                block()
            }
        } else {
            block()
        }
    }

}
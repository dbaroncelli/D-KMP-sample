package eu.baroncelli.dkmpsample.shared.datalayer

import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localsettings.MySettings
import eu.baroncelli.dkmpsample.shared.datalayer.sources.runtimecache.CacheObjects
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.ApiClient
import kotlinx.coroutines.*
import mylocal.db.LocalDb

class Repository (val useDefaultDispatcher : Boolean, val sqlDriver : SqlDriver, val settings : Settings = Settings()) {

    internal val webservices by lazy { ApiClient() }
    internal val localDb by lazy { LocalDb(sqlDriver) }
    val localSettings by lazy { MySettings(settings) }
    internal val runtimeCache by lazy { CacheObjects() }


    // if useDefaultDispatcher is TRUE, we run repository functions in Dispatchers.Default
    // otherwise, we run them in the originating coroutine dispatcher, which is Dispatchers.Main
    // NOTE: currently we are passing useDefaultDispatcher=TRUE only for Android
    // on iOS we will wait for the new Kotlin/Native memory model to become available
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
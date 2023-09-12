package eu.baroncelli.dkmpsample.shared.datalayer

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.russhwolf.settings.Settings
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localdb.countries.Countries
import eu.baroncelli.dkmpsample.shared.datalayer.sources.localsettings.MySettings
import eu.baroncelli.dkmpsample.shared.datalayer.sources.runtimecache.CacheObjects
import eu.baroncelli.dkmpsample.shared.datalayer.sources.webservices.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mylocal.db.LocalDb

class Repository(
    val sqlDriver: SqlDriver,
    val settings: Settings = Settings(),
    val useDefaultDispatcher: Boolean = true
) {

    internal val webservices by lazy { ApiClient() }
    internal val localDb by lazy {
        val intToLongColumnAdapter = object : ColumnAdapter<Int, Long> {
            override fun decode(databaseValue: Long): Int {
                return databaseValue.toInt()
            }

            override fun encode(value: Int): Long {
                return value.toLong()
            }
        }
        LocalDb.invoke(
            sqlDriver,
            Countries.Adapter(intToLongColumnAdapter, intToLongColumnAdapter, intToLongColumnAdapter)
        )
    }
    internal val localSettings by lazy { MySettings(settings) }
    internal val runtimeCache get() = CacheObjects

    // we run each repository function on a Dispatchers.Default coroutine
    // we pass useDefaultDispatcher=false just for the TestRepository instance
    suspend fun <T> withRepoContext(block: suspend () -> T): T {
        return if (useDefaultDispatcher) {
            withContext(Dispatchers.Default) {
                block()
            }
        } else {
            block()
        }
    }

}
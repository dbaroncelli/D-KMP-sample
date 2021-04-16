package eu.baroncelli.dkmpsample.shared

import com.russhwolf.settings.MockSettings
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import mylocal.db.LocalDb
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

actual val testCoroutineContext: CoroutineContext =
    Executors.newSingleThreadExecutor().asCoroutineDispatcher()
actual fun runBlockingTest(block: suspend CoroutineScope.() -> Unit) =
    runBlocking(testCoroutineContext) { this.block() }

actual fun getTestRepository() : Repository {
    val sqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    LocalDb.Schema.create(sqlDriver)
    return Repository(sqlDriver, MockSettings(), false)
}
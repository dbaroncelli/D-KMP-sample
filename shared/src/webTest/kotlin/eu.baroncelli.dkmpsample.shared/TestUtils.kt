package eu.baroncelli.dkmpsample.shared

import com.russhwolf.settings.MockSettings
import com.squareup.sqldelight.db.SqlDriver
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

actual val testCoroutineContext: CoroutineContext = EmptyCoroutineContext

actual fun runBlockingTest(block: suspend CoroutineScope.() -> Unit) { }

actual fun getTestRepository() : Repository {
    lateinit var sqlDriver : SqlDriver
    return Repository(sqlDriver, MockSettings(), false)
}
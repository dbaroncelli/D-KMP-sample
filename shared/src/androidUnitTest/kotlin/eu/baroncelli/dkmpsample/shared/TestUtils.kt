package eu.baroncelli.dkmpsample.shared

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.russhwolf.settings.MapSettings
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import mylocal.db.LocalDb

actual suspend fun getTestRepository(): Repository {
    val sqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    LocalDb.Schema.create(sqlDriver)
    return Repository(sqlDriver, MapSettings(), false)
}
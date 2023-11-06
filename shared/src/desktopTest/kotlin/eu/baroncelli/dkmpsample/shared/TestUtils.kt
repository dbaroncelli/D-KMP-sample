package eu.baroncelli.dkmpsample.shared

import com.russhwolf.settings.MapSettings
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import mylocal.db.LocalDb

actual fun getTestRepository() : Repository {
    val sqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    LocalDb.Schema.create(sqlDriver)
    return Repository(sqlDriver, MapSettings(), true)
}
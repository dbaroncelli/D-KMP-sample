package eu.baroncelli.dkmpsample.shared

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.russhwolf.settings.MapSettings
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import mylocal.db.LocalDb

actual fun getTestRepository(): Repository {
    val sqlDriver = NativeSqliteDriver(LocalDb.Schema, "test.db")
    return Repository(sqlDriver, MapSettings())
}
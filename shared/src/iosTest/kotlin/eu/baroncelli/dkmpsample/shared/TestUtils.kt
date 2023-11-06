package eu.baroncelli.dkmpsample.shared

import com.russhwolf.settings.MapSettings
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import mylocal.db.LocalDb


actual fun getTestRepository() : Repository {
    val sqlDriver = NativeSqliteDriver(LocalDb.Schema, "test.db")
    return Repository(sqlDriver, MapSettings(), true)
}
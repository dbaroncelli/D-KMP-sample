package eu.baroncelli.dkmpsample.shared.viewmodel

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import mylocal.db.LocalDb
import java.io.File


fun DKMPViewModel.Factory.getDesktopInstance() : DKMPViewModel {
    val databasePath = File(System.getProperty("java.io.tmpdir"), "Local.db")
    val sqlDriver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}")
    try { LocalDb.Schema.create(sqlDriver) } catch (e: Exception) { }
    val repository = Repository(sqlDriver)
    return DKMPViewModel(repository)
}
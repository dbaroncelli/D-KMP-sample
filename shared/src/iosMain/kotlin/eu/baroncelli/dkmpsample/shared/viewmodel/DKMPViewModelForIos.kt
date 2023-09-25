package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import io.ktor.utils.io.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import mylocal.db.LocalDb


fun DKMPViewModel.Factory.getIosInstance() : DKMPViewModel {
    val sqlDriver = NativeSqliteDriver(LocalDb.Schema, "Local.db")
    val repository = Repository(sqlDriver)
    return DKMPViewModel(repository)
}
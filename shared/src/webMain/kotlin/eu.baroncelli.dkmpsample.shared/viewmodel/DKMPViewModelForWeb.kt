package eu.baroncelli.dkmpsample.shared.viewmodel

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import kotlinx.coroutines.await
import mylocal.db.LocalDb

suspend fun DKMPViewModel.Factory.getWebInstance() : DKMPViewModel {
    val sqlDriver: SqlDriver = initSqlDriver(LocalDb.Schema).await()
    val repository = Repository(sqlDriver)
    return DKMPViewModel(repository)
}
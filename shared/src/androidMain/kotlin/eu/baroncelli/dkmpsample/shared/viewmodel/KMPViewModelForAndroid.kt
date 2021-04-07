package eu.baroncelli.dkmpsample.shared.viewmodel

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import kotlinx.coroutines.Dispatchers
import mylocal.db.LocalDb


fun KMPViewModel.Factory.getAndroidInstance(context : Context) : KMPViewModel {
    val sqlDriver = AndroidSqliteDriver(LocalDb.Schema, context, "Local.db")
    val repository = Repository(Dispatchers.Default, sqlDriver)
    return KMPViewModel(repository)
}
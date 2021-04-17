package eu.baroncelli.dkmpsample.shared.viewmodel

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import mylocal.db.LocalDb


fun DKMPViewModel.Factory.getAndroidInstance(context : Context) : DKMPViewModel {
    val sqlDriver = AndroidSqliteDriver(LocalDb.Schema, context, "Local.db")
    val repository = Repository(sqlDriver)
    return DKMPViewModel(repository)
}
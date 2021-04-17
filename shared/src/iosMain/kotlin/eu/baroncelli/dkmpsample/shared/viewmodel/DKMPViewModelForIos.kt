package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import io.ktor.utils.io.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import mylocal.db.LocalDb


fun DKMPViewModel.Factory.getIosInstance() : DKMPViewModel {
    val sqlDriver = NativeSqliteDriver(LocalDb.Schema, "Local.db")
    val repository = Repository(sqlDriver)
    return DKMPViewModel(repository)
}


// this is required, because default arguments of Kotlin functions are currently not exposed to Objective-C or Swift
// https://youtrack.jetbrains.com/issue/KT-41908
fun DKMPViewModel.getDefaultAppState() : AppState {
    return AppState()
}

// this function notifies of any state changes to the iOS AppObservableObject class
// hopefully this code will eventually be provided by an official Kotlin function
// https://youtrack.jetbrains.com/issue/KT-41953
fun DKMPViewModel.onChange(provideNewState: ((AppState) -> Unit)) : Closeable {

    val job = Job()

    stateFlow.onEach {
        provideNewState(it)
    }.launchIn(
        CoroutineScope(Dispatchers.Main + job)
    )

    return object : Closeable {
        override fun close() {
            job.cancel()
        }
    }

}
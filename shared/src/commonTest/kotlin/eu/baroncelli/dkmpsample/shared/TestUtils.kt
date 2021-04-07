package eu.baroncelli.dkmpsample.shared

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenState
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager
import eu.baroncelli.dkmpsample.shared.viewmodel.getScreenType
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

expect fun runBlockingTest(block: suspend CoroutineScope.()-> Unit)
expect val testCoroutineContext: CoroutineContext

expect fun getTestRepository() : Repository
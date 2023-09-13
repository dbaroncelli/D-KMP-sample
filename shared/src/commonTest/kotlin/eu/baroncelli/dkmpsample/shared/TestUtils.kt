package eu.baroncelli.dkmpsample.shared

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

expect suspend fun getTestRepository() : Repository
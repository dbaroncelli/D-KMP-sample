package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.DebugLogger
import eu.baroncelli.dkmpsample.shared.datalayer.Repository

val debugLogger by lazy { DebugLogger("D-KMP SAMPLE") }


class DKMPViewModel(repo: Repository) {

    companion object Factory {
        // factory methods are defined in the platform-specific shared code (androidMain and iosMain)
    }

    private val stateManager by lazy { StateManager(repo) }
    val navigation by lazy { Navigation(stateManager) }

}
package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.viewmodel.detail.DetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.master.MasterState

data class AppState (
    internal val masterState : MasterState = MasterState(),
    internal val detailState : DetailState = DetailState(),
) {
    fun getStateProvider(model : KMPViewModel) : StateProvider {
        return StateProvider(model)
    }
}
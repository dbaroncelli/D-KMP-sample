package eu.baroncelli.dkmpsample.shared.viewmodel.appstate

import eu.baroncelli.dkmpsample.shared.viewmodel.appstate.detail.DetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.appstate.master.MasterState

data class AppState (
    val masterState : MasterState = MasterState(),
    val detailState : DetailState = DetailState(),
)
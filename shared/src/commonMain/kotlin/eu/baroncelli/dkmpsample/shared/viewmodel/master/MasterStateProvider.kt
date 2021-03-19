package eu.baroncelli.dkmpsample.shared.viewmodel.master


import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger


fun StateProvider.getMaster() : MasterState {

    /********** RUN UPDATE, BASED ON CONDITION **********/
    val condition = getState().masterState.selectedMenuItem == MenuItem.UNDEFINED
    debugLogger.d{"getMaster() menuItem: "+getState().masterState.selectedMenuItem+" / RUN UPDATE "+ condition}
    if (condition) {
        events.updateMasterData()
    }

    /********** RETURN STATE OBJECT **********/
    return getState().masterState
}
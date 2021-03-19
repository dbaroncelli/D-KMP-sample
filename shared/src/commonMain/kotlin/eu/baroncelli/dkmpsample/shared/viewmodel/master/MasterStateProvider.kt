package eu.baroncelli.dkmpsample.shared.viewmodel.master


import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger


fun StateProvider.getMaster() : MasterState {

    /********** LOAD DATA, BASED ON CONDITION **********/
    // condition should be set to that the StateProvider should load the data only once
    // make sure your condition is correct, otherwise the risk is to load data at each single
    // declarative UI recomposition, which you definitely want to avoid!
    val condition = getState().masterState.selectedMenuItem == MenuItem.UNDEFINED
    //debugLogger.d{"MasterStateProvide: "+getState().masterState.selectedMenuItem+" / LOAD DATA "+ condition}
    if (condition) {
        events.loadMasterData()
    }

    /********** RETURN STATE OBJECT **********/
    return getState().masterState
}
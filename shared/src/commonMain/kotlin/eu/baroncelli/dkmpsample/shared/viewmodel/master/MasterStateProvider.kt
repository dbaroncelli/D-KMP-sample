package eu.baroncelli.dkmpsample.shared.viewmodel.master


import eu.baroncelli.dkmpsample.shared.viewmodel.StateProvider
import eu.baroncelli.dkmpsample.shared.viewmodel.debugLogger


fun StateProvider.getMaster() : MasterState {

    /********** LOAD DATA, BASED ON CONDITION **********/
    // condition should be choosen so that the StateProvider doesn't attempt to reload the data when it's already there
    // make sure your condition is correct, otherwise the risk is to go into a recomposition loop
    val condition = getState().masterState.selectedMenuItem == MenuItem.UNDEFINED
    // in this case we load the data only if the MenuItem is still undefined, which means no data has been loaded yet
    //debugLogger.d{"MasterStateProvide: "+getState().masterState.selectedMenuItem+" / LOAD DATA "+ condition}
    if (condition) {
        events.loadMasterData()
    }

    /********** RETURN STATE OBJECT **********/
    return getState().masterState
}
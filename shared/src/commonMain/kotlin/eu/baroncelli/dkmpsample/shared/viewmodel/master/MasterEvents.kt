package eu.baroncelli.dkmpsample.shared.viewmodel.master

import eu.baroncelli.dkmpsample.shared.viewmodel.Events

/********** INTERNAL EVENT FUNCTION, USED BY THE STATE PROVIDER **********/

internal fun Events.loadMasterData() {
    val restoredSelectedMenuItem = stateManager.restoreSelectedMenuItem()
    // debugLogger.d{"restoredSelectedMenuItem: "+restoredSelectedMenuItem}
    // launch a coroutine, as "setMasterDataByMenuItem" is a suspend function
    launchCoroutine {
        stateManager.setMasterDataByMenuItem(restoredSelectedMenuItem)
    }
}


/********** PUBLIC EVENT FUNCTIONS **********/

fun Events.selectMenuItem(menuItem: MenuItem) {
    // launch a coroutine, as "setMasterDataByMenuItem" is a suspend function
    launchCoroutine {
        stateManager.setMasterDataByMenuItem(menuItem)
    }
}

fun Events.selectFavorite(country: String) {
    stateManager.toggleFavorite(country)
}
package eu.baroncelli.dkmpsample.shared.viewmodel.master

import eu.baroncelli.dkmpsample.shared.viewmodel.Events

/********** INTERNAL EVENT FUNCTION, USED BY THE STATE PROVIDER **********/

internal fun Events.loadMasterData() {
    val restoredSelectedMenuItem = stateManager.restoreSelectedMenuItem()
    launchCoroutine {
        // debugLogger.d{"restoredSelectedMenuItem: "+restoredSelectedMenuItem}
        stateManager.getDataByMenuItem(restoredSelectedMenuItem)
    }
}


/********** PUBLIC EVENT FUNCTIONS **********/

fun Events.selectMenuItem(menuItem: MenuItem) {
    launchCoroutine {
        stateManager.getDataByMenuItem(menuItem)
    }
}

fun Events.selectFavorite(country: String) {
    stateManager.selectFavorite(country)
}
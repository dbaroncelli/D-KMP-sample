package eu.baroncelli.dkmpsample.shared.viewmodel.events

import eu.baroncelli.dkmpsample.shared.viewmodel.KMPViewModel
import eu.baroncelli.dkmpsample.shared.viewmodel.appstate.master.MenuItem
import eu.baroncelli.dkmpsample.shared.viewmodel.statereducers.*


fun KMPViewModel.appStarted() {
    launchCoroutine {
        val restoredSelectedMenuItem = stateManager.restoreSelectedMenuItem()
        // debugLogger.d{"restoredSelectedMenuItem: "+restoredSelectedMenuItem}
        stateManager.getDataByMenuItem(restoredSelectedMenuItem)
    }
}

fun KMPViewModel.selectMenuItem(menuItem: MenuItem) {
    launchCoroutine {
        stateManager.getDataByMenuItem(menuItem)
    }
}

fun KMPViewModel.selectFavorite(country: String) {
    stateManager.selectFavorite(country)
}
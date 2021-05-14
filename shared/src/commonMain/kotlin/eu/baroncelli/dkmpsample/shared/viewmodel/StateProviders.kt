package eu.baroncelli.dkmpsample.shared.viewmodel

class StateProviders (val stateManager : StateManager) {

    inline fun <reified T: ScreenState> get(screenIdentifier: ScreenIdentifier) : T {
        return stateManager.screenStatesMap[screenIdentifier] as T
    }

}
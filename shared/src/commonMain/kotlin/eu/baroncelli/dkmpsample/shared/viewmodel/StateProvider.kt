package eu.baroncelli.dkmpsample.shared.viewmodel

class StateProvider (val stateManager : StateManager) {

    inline fun <reified T: ScreenState> get(screenIdentifier: ScreenIdentifier) : T {
        return stateManager.screenStatesMap[screenIdentifier] as T
    }

}
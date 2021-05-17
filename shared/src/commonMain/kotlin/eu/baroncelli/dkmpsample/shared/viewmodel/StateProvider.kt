package eu.baroncelli.dkmpsample.shared.viewmodel

class StateProvider (val stateManager : StateManager) {

    inline fun <reified T: ScreenState> get(screenIdentifier: ScreenIdentifier) : T {
        return stateManager.screenStatesMap[screenIdentifier] as T
    }

    // iOS cannot use Reified functions, so we use this one with the "ScreenState" interface return type
    // on Swift, we then need to cast it to the specific state class
    fun getToCast(screenIdentifier: ScreenIdentifier) : ScreenState? {
        return stateManager.screenStatesMap[screenIdentifier]
    }

}




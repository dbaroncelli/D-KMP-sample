package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KClass


interface ScreenState
interface ScreenParams

class StateManager(repo: Repository) {

    internal val mutableStateFlow = MutableStateFlow(AppState())

    val screenStatesMap : MutableMap<URI,ScreenState> = mutableMapOf() // map of screen states currently in memory
    val screenScopesMap : MutableMap<URI,CoroutineScope> = mutableMapOf() // map of coroutine scopes associated to current screen states

    val level1Backstack: MutableList<ScreenIdentifier> = mutableListOf() // list elements are only NavigationLevel1 screenIdentifiers
    val currentVerticalBackstack: MutableList<ScreenIdentifier> = mutableListOf() // list elements are the screenIdentifiers of the current vertical backstack
    val verticalNavigationLevels : MutableMap<URI,MutableMap<Int, ScreenIdentifier>> = mutableMapOf() // the first map key is the NavigationLevel1 screenIdentifier URI, the second map key is the NavigationLevel numbers

    val currentScreenIdentifier : ScreenIdentifier
        get() = currentVerticalBackstack.last()
    val currentLevel1ScreenIdentifier : ScreenIdentifier?
        get() = level1Backstack.lastOrNull()
    val currentVerticalNavigationLevelsMap: MutableMap<Int, ScreenIdentifier>
        get() = verticalNavigationLevels[currentLevel1ScreenIdentifier?.URI] ?: mutableMapOf()

    internal val dataRepository by lazy { repo }


    fun triggerAppStateRecomposition() {
        mutableStateFlow.value = AppState(mutableStateFlow.value.recompositionIndex+1)
    }



    // INIT SCREEN

    fun initScreen(screenIdentifier: ScreenIdentifier) {
        debugLogger.log("initScreen: "+screenIdentifier.URI)
        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        if (!isInTheStatesMap(screenIdentifier)) {
            screenStatesMap[screenIdentifier.URI] = screenInitSettings.initState(screenIdentifier)
        } else if (!screenInitSettings.callOnInitAtEachNavigation) {
            return // in case the state is already in the map and the callOnInitAtEachNavigation is not enabled
                    // we don't need to run the "callOnInit"
        }
        if (screenScopesMap[screenIdentifier.URI] == null || !screenScopesMap[screenIdentifier.URI]!!.isActive) {
            screenScopesMap[screenIdentifier.URI]?.cancel()
            screenScopesMap[screenIdentifier.URI] = CoroutineScope(Job() + Dispatchers.Main)
        }
        runInScreenScope(screenIdentifier) {
            screenInitSettings.callOnInit(this)
        }
    }

    fun isInTheStatesMap(screenIdentifier: ScreenIdentifier) : Boolean {
        return screenStatesMap.containsKey(screenIdentifier.URI)
    }




    // UPDATE SCREEN

    inline fun <reified T: ScreenState> updateScreen(
        @Suppress("UNUSED_PARAMETER") stateClass: KClass<T>,
        update: (T) -> T,
    ) {
        debugLogger.log("updateScreen: "+stateClass.simpleName)
        //debugLogger.log("currentVerticalNavigationLevelsMap: "+currentVerticalNavigationLevelsMap.values.map { it.URI } )

        lateinit var screenIdentifier : ScreenIdentifier
        var screenState : T?
        for(i in currentVerticalNavigationLevelsMap.keys.sortedDescending()) {
            screenState = screenStatesMap[currentVerticalNavigationLevelsMap[i]?.URI] as? T
            if (screenState != null) {
                screenIdentifier = currentVerticalNavigationLevelsMap[i]!!
                screenStatesMap[screenIdentifier.URI] = update(screenState)
                debugLogger.log("state updated @ /${screenIdentifier.URI}")
                triggerAppStateRecomposition()
                return
            }
        }
    }




    // REMOVE SCREEN

    fun removeScreen(screenIdentifier: ScreenIdentifier) {
        debugLogger.log("removeScreen: "+screenIdentifier.URI+" / level "+screenIdentifier.screen.navigationLevel)
        screenScopesMap[screenIdentifier.URI]?.cancel() // cancel screen's coroutine scope
        screenScopesMap.remove(screenIdentifier.URI)
        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        if (screenInitSettings.clearStateCacheWhenScreenIsRemovedFromBackstack) {
            debugLogger.log("removeState "+screenIdentifier.URI)
            screenStatesMap.remove(screenIdentifier.URI)
        }
    }





    // COROUTINE SCOPES FUNCTIONS

    fun reinitScreenScopes() : List<ScreenIdentifier> {
        currentVerticalNavigationLevelsMap.forEach {
            //debugLogger.log("reinitScreenScopes() "+it.value.URI)
            screenScopesMap[it.value.URI] = CoroutineScope(Job() + Dispatchers.Main)
        }
        return currentVerticalNavigationLevelsMap.values.toMutableList() // return list of screens whose scope has been reinitialized
    }

    // we run each event function on a Dispatchers.Main coroutine
    fun runInScreenScope (screenIdentifier: ScreenIdentifier? = null, block: suspend () -> Unit) {
        val URI = screenIdentifier?.URI ?: currentScreenIdentifier.URI
        val screenScope = screenScopesMap[URI]
        screenScope?.launch {
            block()
        }
    }

    fun cancelScreenScopes() {
        screenScopesMap.forEach {
            //debugLogger.log("cancelScreenScopes() "+it.key)
            it.value.cancel() // cancel screen's coroutine scope
        }
    }

}



// APPSTATE DATA CLASS DEFINITION

data class AppState (
    val recompositionIndex : Int = 0,
) {
    fun getNavigation(model: DKMPViewModel) : Navigation {
        return model.navigation
    }
}
package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.ScreenInitSettings
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.navigationSettings
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

    var skipNextUpdateScreenRecomposition = false

    internal val dataRepository by lazy { repo }

    val currentScreenIdentifier : ScreenIdentifier?
        get() = currentVerticalBackstack.lastOrNull()
    val currentLevel1ScreenIdentifier : ScreenIdentifier?
        get() = level1Backstack.lastOrNull()
    val currentVerticalNavigationLevelsMap: MutableMap<Int, ScreenIdentifier>
        get() = verticalNavigationLevels[currentLevel1ScreenIdentifier?.URI] ?: mutableMapOf()
    val only1ScreenInBackstack : Boolean
        get() = level1Backstack.size + currentVerticalBackstack.size == 2


    fun isInTheStatesMap(screenIdentifier: ScreenIdentifier) : Boolean {
        return screenStatesMap.containsKey(screenIdentifier.URI)
    }

    fun isInAnyVerticalNavigationLevel(screenIdentifier: ScreenIdentifier) : Boolean {
        verticalNavigationLevels.forEach { verticalNavigation ->
            verticalNavigation.value.forEach {
                if (it.value.URI == screenIdentifier.URI) {
                    return true
                }
            }
        }
        return false
    }

    inline fun <reified T: ScreenState> updateScreen(
            @Suppress("UNUSED_PARAMETER") stateClass: KClass<T>,
            update: (T) -> T,
    ) {
        //debugLogger.log("updateScreen: "+currentScreenIdentifier!!.URI)

        lateinit var screenIdentifier : ScreenIdentifier
        var screenState : T?
        for(i in currentVerticalNavigationLevelsMap.keys.sortedDescending()) {
            screenState = screenStatesMap[currentVerticalNavigationLevelsMap[i]?.URI] as? T
            if (screenState != null) {
                screenIdentifier = currentVerticalNavigationLevelsMap[i]!!
                screenStatesMap[screenIdentifier.URI] = update(screenState)
                debugLogger.log("state updated @ /${screenIdentifier.URI}")
                if (skipNextUpdateScreenRecomposition) {
                    skipNextUpdateScreenRecomposition = false
                    return
                }
                triggerRecomposition()
                return
            }
        }
    }


    fun triggerRecomposition() {
        mutableStateFlow.value = AppState(mutableStateFlow.value.recompositionIndex+1)
    }


    inline fun <reified T: ScreenState> requestScreenState(screenIdentifier: ScreenIdentifier) : T {
        //debugLogger.log("requestScreenState: "+screenIdentifier.URI)
        if (!currentVerticalBackstack.any { it.URI == screenIdentifier.URI }) {
            debugLogger.log("adding to backstack "+screenIdentifier.URI)
            addScreenToBackstack(screenIdentifier)
            val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
            if (!isInTheStatesMap(screenIdentifier)) {
                initScreen(screenIdentifier, screenInitSettings, true)
            } else if (screenInitSettings.reinitOnEachNavigation) {
                initScreen(screenIdentifier, screenInitSettings, false)
            }
        }
        return screenStatesMap[screenIdentifier.URI] as T
    }


    // INIT SCREEN FUNCTIONS

    fun initScreen(screenIdentifier: ScreenIdentifier, screenInitSettings: ScreenInitSettings, initState: Boolean) {
        //debugLogger.log("initScreen: "+screenIdentifier.URI+ " / "+ initStateAndScope)
        if (initState) {
            screenStatesMap[screenIdentifier.URI] = screenInitSettings.initState(screenIdentifier)
        }
        if (screenScopesMap[screenIdentifier.URI] == null || !screenScopesMap[screenIdentifier.URI]!!.isActive) {
            screenScopesMap[screenIdentifier.URI]?.cancel()
            screenScopesMap[screenIdentifier.URI] = CoroutineScope(Job() + Dispatchers.Main)
        }
        if (screenInitSettings.runCallOnInitBeforeNavigationTransition) {
            runBlocking {
                skipNextUpdateScreenRecomposition = true
                screenInitSettings.callOnInit(this@StateManager)
            }
        } else {
            runInScreenScope(screenIdentifier) {
                screenInitSettings.callOnInit(this@StateManager)
            }
        }
        //debugLogger.log("currentVerticalBackstack: "+currentVerticalBackstack.map{it.URI}.toString())
        //debugLogger.log("currentVerticalNavigationLevelsMap: "+currentVerticalNavigationLevelsMap.values.map{it.URI}.toString())
        //debugLogger.log("level1Backstack: "+level1Backstack.map{it.URI}.toString())
        //debugLogger.log("screenScopesMap: "+screenScopesMap.keys.map{it}.toString())
        //debugLogger.log("screenStatesMap: "+screenStatesMap.keys.map{it}.toString())
    }

    fun addScreenToBackstack(screenIdentifier: ScreenIdentifier) {
        if (screenIdentifier.screen.navigationLevel == 1) {
            if (level1Backstack.size > 0) {
                clearLevel1Screen(currentLevel1ScreenIdentifier!!)
            }
            setupNewLevel1Screen(screenIdentifier)
        } else {
            if (currentVerticalBackstack.last().URI != screenIdentifier.URI) {
                currentVerticalBackstack.add(screenIdentifier)
            }
        }
        currentVerticalNavigationLevelsMap[screenIdentifier.screen.navigationLevel] = screenIdentifier
    }




    // REMOVE SCREEN FUNCTIONS

    fun removeScreen(screenIdentifier: ScreenIdentifier) {
        //debugLogger.log("removeScreen /"+screenIdentifier.URI+" level "+screenIdentifier.screen.navigationLevel)
        if (screenIdentifier.screen.navigationLevel == 1) {
            level1Backstack.remove(screenIdentifier)
            removeScreenStateAndScope(screenIdentifier)
        } else {
            currentVerticalNavigationLevelsMap.remove(screenIdentifier.screen.navigationLevel)
            currentVerticalBackstack.removeAll { it.URI == screenIdentifier.URI }
            currentVerticalNavigationLevelsMap[currentScreenIdentifier!!.screen.navigationLevel] = currentScreenIdentifier!! // set new currentScreenIdentifier, after the removal
            if (!isInAnyVerticalNavigationLevel(screenIdentifier)) {
                removeScreenStateAndScope(screenIdentifier)
            }
            val screenInitSettings = currentScreenIdentifier!!.getScreenInitSettings(this)
            if (screenInitSettings.reinitOnEachNavigation) {
                initScreen(screenIdentifier, screenInitSettings, false)
            }
        }
    }

    fun removeScreenStateAndScope(screenIdentifier: ScreenIdentifier) {
        //debugLogger.log("removeScreenStateAndScope /"+screenIdentifier.URI)
        screenScopesMap[screenIdentifier.URI]?.cancel() // cancel screen's coroutine scope
        screenScopesMap.remove(screenIdentifier.URI)
        screenStatesMap.remove(screenIdentifier.URI)
    }



    // LEVEL 1 NAVIGATION FUNCTIONS

    fun clearLevel1Screen(currentLevel1ScreenIdentifier: ScreenIdentifier) {
        //debugLogger.log("clear level 1 backstack /"+screenIdentifier.URI)
        if (currentLevel1ScreenIdentifier.level1VerticalBackstackEnabled()) {
            currentVerticalBackstack.filter {
                it.URI !in currentVerticalNavigationLevelsMap.values.map { levelScreen -> levelScreen.URI }
            }.forEach {
                removeScreenStateAndScope(it)
            }
        } else {
            currentVerticalBackstack.filter {
                it.screen.navigationLevel > 1
            }.forEach {
                removeScreenStateAndScope(it)
            }
            currentVerticalNavigationLevelsMap.keys.removeAll { it != 1 }
        }
    }

    fun setupNewLevel1Screen(screenIdentifier: ScreenIdentifier) {
        level1Backstack.removeAll { it.URI == screenIdentifier.URI }
        if (navigationSettings.alwaysQuitOnHomeScreen) {
            if (screenIdentifier.URI == navigationSettings.homeScreen.screenIdentifier.URI) {
                level1Backstack.clear() // remove all elements
            } else if (level1Backstack.size == 0) {
                level1Backstack.add(navigationSettings.homeScreen.screenIdentifier)
            }
        }
        level1Backstack.add(screenIdentifier)
        currentVerticalBackstack.clear()
        currentVerticalBackstack.add(screenIdentifier)
        verticalNavigationLevels[screenIdentifier.URI] = mutableMapOf()
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
        val URI = screenIdentifier?.URI ?: currentScreenIdentifier!!.URI
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
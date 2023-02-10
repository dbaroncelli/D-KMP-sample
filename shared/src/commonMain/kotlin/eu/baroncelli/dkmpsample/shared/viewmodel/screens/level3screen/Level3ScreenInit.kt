package eu.baroncelli.dkmpsample.shared.viewmodel.screens.level3screen

import eu.baroncelli.dkmpsample.shared.viewmodel.Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.ScreenParams
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable

@Serializable // Note: ScreenParams should always be set as Serializable
data class Level3ScreenParams(val name: String) : ScreenParams

fun StateManager.initLevel3Screen(params: Level3ScreenParams) = ScreenInitSettings (
    title = params.name,
    initState = {
        Level3ScreenState(
            text = "OK!"
        )
    },
    callOnInit = { }
)
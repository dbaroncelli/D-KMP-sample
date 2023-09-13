package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Level1Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.ScreenInitSettings
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.ScreenParams
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

typealias URI = String

class ScreenParamsDeserializationException(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause)

class ScreenIdentifier private constructor(
    val screen: Screen,
    var params: ScreenParams? = null,
) {
    val URI: URI
        get() = returnURI()


    companion object Factory {

        internal val json = Json

        fun get(screen: Screen, params: ScreenParams?): ScreenIdentifier {
            return ScreenIdentifier(screen, params)
        }

        fun getByURI(URI: String): ScreenIdentifier? {
            val splitAt = URI.indexOf(':')
            val part0 = URI.substring(0, splitAt)
            val part1 = URI.substring(splitAt + 1)
            Screen.entries.forEach {
                if (it.asString == part0) {
                    val jsonString = if (part1 == "null") null else part1
                    val params: ScreenParams? = try {
                        jsonString?.let { json.decodeFromString(jsonString) }
                    } catch (t: Throwable) {
                        throw ScreenParamsDeserializationException(
                            "Failed to deserialize params for screen: ${it.asString}",
                            t
                        )
                    }
                    return get(it, params)
                }
            }
            return null
        }

    }

    private fun returnURI(): String {
        val paramsString = if (params != null) {
            json.encodeToString(params)
        } else {
            "null"
        }
        return screen.asString + ":" + paramsString
    }

    // unlike the "params" property, this reified function returns the specific type and not the generic "ScreenParams" interface type
    inline fun <reified T : ScreenParams> params(): T {
        return try {
            params as T
        } catch (t: Throwable) {
            if (screen.navigationLevel == 1) {
                val defaultParams = Level1Navigation.entries.first {
                    it.screenIdentifier.screen.asString == screen.asString
                }.screenIdentifier.params
                debugLogger.log(
                    "Warning: Failed to cast params: $params as ${T::class} returning default L1 params for screen: " + "$defaultParams"
                )
                defaultParams as T
            } else {
                throw t
            }
        }
    }

    fun getScreenInitSettings(stateManager: StateManager): ScreenInitSettings {
        return screen.initSettings(stateManager, this)
    }

    fun level1VerticalBackstackEnabled(): Boolean {
        Level1Navigation.entries.forEach {
            if (it.screenIdentifier.URI == this.URI && it.rememberVerticalStack) {
                return true
            }
        }
        return false
    }

}
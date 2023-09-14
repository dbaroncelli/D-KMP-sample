package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Level1Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.json.Json

typealias URI = String

class ScreenIdentifier private constructor(
    val screen: Screen,
    var params: ScreenParams? = null,
    var paramsAsString: String? = null,
) {

    val URI: URI
        get() = returnURI()

    companion object Factory {
        fun get(screen: Screen, params: ScreenParams?): ScreenIdentifier {
            return ScreenIdentifier(screen, params, null)
        }

        fun getByURI(URI: String): ScreenIdentifier? {
            val parts = URI.split(":")
            Screen.values().forEach {
                if (it.asString == parts[0]) {
                    return ScreenIdentifier(it, null, parts[1])
                }
            }
            return null
        }

    }

    private fun returnURI(): String {
        if (paramsAsString != null) {
            return screen.asString + ":" + paramsAsString
        }
        val toString = params.toString() // returns `ClassParams(A=1&B=2)`
        val startIndex = toString.indexOf("(")
        val paramsString = toString.substring(startIndex + 1, toString.length - 1)
        return screen.asString + ":" + paramsString
    }

    // unlike the "params" property, this reified function returns the specific type and not the generic "ScreenParams" interface type
    inline fun <reified T : ScreenParams> params(): T {
        if (params == null && paramsAsString != null) {
            val jsonValues = paramsStrToJson(paramsAsString!!)
            params = Json.decodeFromString<T>("""{$jsonValues}""")
        }
        return params as T
    }

    fun paramsStrToJson(paramsAsString: String): String {
        // converts `A=1&B=1` into `"A":"1","B":"2"`
        val elements = paramsAsString.split("&")
        var jsonValues = ""
        elements.forEach {
            if (jsonValues != "") {
                jsonValues += ","
            }
            val parts = it.split("=")
            jsonValues += "\"${parts[0]}\":\"${parts[1]}\""
        }
        return jsonValues
    }


    fun getScreenInitSettings(stateManager: StateManager): ScreenInitSettings {
        return screen.initSettings(stateManager, this)
    }

    fun level1VerticalBackstackEnabled(): Boolean {
        Level1Navigation.values().forEach {
            if (it.screenIdentifier.URI == this.URI && it.rememberVerticalStack) {
                return true
            }
        }
        return false
    }

}
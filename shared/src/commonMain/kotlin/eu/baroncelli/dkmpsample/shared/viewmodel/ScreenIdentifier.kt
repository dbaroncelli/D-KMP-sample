package eu.baroncelli.dkmpsample.shared.viewmodel

import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Level1Navigation
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Screen
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


data class ScreenIdentifier (
    val screen : Screen,
    var params: ScreenParams? = null,
    var paramsAsString: String? = null,
) {

    val URI : String
        get() = screen.asString+":"+paramsString()

    // unlike the "params" property, this reified function returns the specific type and not the generic "ScreenParams" interface type
    inline fun <reified T: ScreenParams> params() : T {
        if (params == null && paramsAsString != null) {
            val jsonValues = paramsStrToJson(paramsAsString!!)
            params = Json.decodeFromString<T>("""{$jsonValues}""")
            paramsAsString = null
        }
        return params as T
    }

    fun paramsStrToJson(paramsAsString: String) : String {
        // converts `A=1&B=1` into `"A":"1","B":"2"`
        val elements = paramsAsString.split("&")
        var jsonValues = ""
        elements.forEach {
            if (jsonValues!="") {
                jsonValues += ","
            }
            val parts = it.split("=")
            jsonValues += "\"${parts[0]}\":\"${parts[1]}\""
        }
        return jsonValues
    }

    private fun paramsString() : String {
        // returns paramsAsString: `A=1&B=2`
        if (paramsAsString != null) {
            return paramsAsString!!
        } else if (params == null) {
            return ""
        }
        val toString = params.toString() // returns `ClassParams(A=1&B=2)`
        val startIndex = toString.indexOf("(")
        return toString.substring(startIndex+1,toString.length-1)
    }

    fun getScreenInitSettings(navigation: Navigation) : ScreenInitSettings {
        return screen.initSettings(navigation,this)
    }

    fun level1VerticalBackstackEnabled() : Boolean {
        Level1Navigation.values().forEach {
            if (it.screenIdentifier == this && it.rememberVerticalStack) {
                return true
            }
        }
        return false
    }

    companion object {
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

}
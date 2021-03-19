package eu.baroncelli.dkmpsample.shared.datalayer.sources.localsettings

import kotlin.reflect.KProperty
import com.russhwolf.settings.Settings
import eu.baroncelli.dkmpsample.shared.viewmodel.master.MenuItem

open class SettingsClient(settingsImpl : Settings) {

    val settings = settingsImpl

    // this is for a standard string
    inner class StringType(defaultValue : String) {
        private val default = defaultValue
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return settings.getString(property.name, default)
        }
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            settings.putString(property.name, value)
        }
    }

    // this is for our custom MenuItem enum
    inner class MenuItemType(defaultValue : MenuItem) {
        private val default = defaultValue
        operator fun getValue(thisRef: Any?, property: KProperty<*>): MenuItem {
            return enumValueOf(settings.getString(property.name, default.name))
        }
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: MenuItem) {
            settings.putString(property.name, value.name)
        }
    }

    // this is for a map of keys with true values (we use it conveniently to store our country favorites)
    // we are storing the map in a Multiplatform string, by simply separating the keys with a "|" symbol
    inner class TrueMapType {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): MutableMap<String,Boolean> {
            val storedValue = settings.getString(property.name, "")
            if (storedValue == "") {
                return mutableMapOf()
            }
            val items = storedValue.split("|")
            var trueMap = mutableMapOf<String,Boolean>()
            for (item in items) {
                trueMap.put(item,true)
            }
            return trueMap
        }
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: MutableMap<String,Boolean>) {
            var string = ""
            for ((country, _) in value) {
                if (string != "") {
                    string += "|"
                }
                string += country
            }
            settings.putString(property.name, string)
        }
    }


}



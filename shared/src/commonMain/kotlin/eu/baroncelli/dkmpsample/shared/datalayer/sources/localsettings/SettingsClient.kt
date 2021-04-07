package eu.baroncelli.dkmpsample.shared.datalayer.sources.localsettings

import kotlin.reflect.KProperty
import com.russhwolf.settings.Settings
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.MenuItem

open class SettingsClient(settingsImpl : Settings) {

    val settings = settingsImpl

    // this is for a long
    inner class LongType(defaultValue : Long) {
        private val default = defaultValue
        operator fun getValue(thisRef: Any?, property: KProperty<*>): Long {
            return settings.getLong(property.name, default)
        }
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
            settings.putLong(property.name, value)
        }
    }

    // this is for a string
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

}



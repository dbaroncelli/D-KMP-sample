package eu.baroncelli.dkmpsample.shared.datalayer.sources.localsettings

import kotlin.reflect.KProperty
import com.russhwolf.settings.Settings
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.MenuItem

open class SettingsClient(settingsImpl : Settings) {

    val settings = settingsImpl


    // we can define here any custom type, using delegated properties: https://kotlinlang.org/docs/delegated-properties.html

    // MenuItem enum
    inner class MenuItemCustomType(defaultValue : MenuItem) {
        private val default = defaultValue
        operator fun getValue(thisRef: Any?, property: KProperty<*>): MenuItem {
            return enumValueOf(settings.getString(property.name, default.name))
        }
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: MenuItem) {
            settings.putString(property.name, value.name)
        }
    }

}



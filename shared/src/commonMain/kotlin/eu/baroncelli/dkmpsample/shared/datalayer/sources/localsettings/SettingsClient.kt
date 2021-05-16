package eu.baroncelli.dkmpsample.shared.datalayer.sources.localsettings

import kotlin.reflect.KProperty
import com.russhwolf.settings.Settings
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Level1Navigation

open class SettingsClient(settingsImpl : Settings) {

    val settings = settingsImpl


    // we can define here any custom type, using delegated properties: https://kotlinlang.org/docs/delegated-properties.html

    // Level1Navigation enum
    inner class Level1NavigationType(defaultValue : Level1Navigation) {
        private val default = defaultValue
        operator fun getValue(thisRef: Any?, property: KProperty<*>): Level1Navigation {
            return enumValueOf(settings.getString(property.name, default.name))
        }
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Level1Navigation) {
            settings.putString(property.name, value.name)
        }
    }

}

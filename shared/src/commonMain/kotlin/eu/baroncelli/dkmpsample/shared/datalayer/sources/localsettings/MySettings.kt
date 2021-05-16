package eu.baroncelli.dkmpsample.shared.datalayer.sources.localsettings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.long
import com.russhwolf.settings.string
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Level1Navigation

class MySettings (s : Settings) : SettingsClient(s) {

    // here we define all our local settings properties:
    // for standard types, we use the MultiplatformSettings delegated properties
    // for custom types, we can define our own delegated properties in the SettingsClient class

    var listCacheTimestamp by s.long(defaultValue = 0)
    var savedLevel1Screen by Level1NavigationType(defaultValue = Level1Navigation.AllCountries)


}
package eu.baroncelli.dkmpsample.shared.datalayer.sources.localsettings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.long
import com.russhwolf.settings.string
import eu.baroncelli.dkmpsample.shared.viewmodel.screens.Level1Navigation

class MySettings(private val s: Settings) {


    // here we define all our local settings properties,
    // by using the MultiplatformSettings library delegated properties

    var listCacheTimestamp by s.long(defaultValue = 0)
    var savedLevel1URI by s.string(defaultValue = Level1Navigation.AllCountries.screenIdentifier.URI)

    fun clear() = s.clear()
}
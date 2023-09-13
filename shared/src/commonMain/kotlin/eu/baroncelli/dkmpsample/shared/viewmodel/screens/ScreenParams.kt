package eu.baroncelli.dkmpsample.shared.viewmodel.screens


import eu.baroncelli.dkmpsample.shared.viewmodel.screens.countrieslist.CountriesListType
import kotlinx.serialization.Serializable

/***
 * [ScreenParams] is an interface which defines the parameters to the passed to the screen if any are needed.
 * Each class which implements it should be a data class and should always be set as [Serializable]
 *
 * Note: we are defining all implementations here instead of inside a screen's
 * eu.baroncelli.dkmpsample.shared.viewmodel.screens.`screen-name` subpackage, because all classes implementing
 * a sealed interface must be in the same package. The reason we are using a sealed interface is that it
 * allows the list of ScreenParams subclasses that can be serialized in a polymorphic way to be
 * determined at compile time vs having to explicitly registered them at runtime.
 * See: https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/polymorphism.md#open-polymorphism
 */
@Serializable
sealed interface ScreenParams

@Serializable
data class CountriesListParams(val listType: CountriesListType) : ScreenParams

@Serializable
data class CountryDetailParams(val countryName: String) : ScreenParams
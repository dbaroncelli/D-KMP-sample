package eu.baroncelli.dkmpsample.shared.viewmodel.screens


// DEFINITION OF ALL SCREENS IN THE APP

enum class Screen(
    val asString: String,
    val navigationLevel : Int = 1,
    val stackableInstances : Boolean = false,
) {
    CountriesList("countrieslist", 1),
    CountryDetail("country", 2),
}



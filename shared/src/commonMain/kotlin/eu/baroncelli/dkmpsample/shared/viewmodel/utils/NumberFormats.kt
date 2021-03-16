package eu.baroncelli.dkmpsample.shared.viewmodel.utils

import kotlin.math.roundToInt

// use commas as thousands delimiter
fun Int.toCommaThousandString() : String {
    val str = this.toString()
    val length = str.length
    var out = ""
    for (i in 0 until length) { // not including (=until) length
        val thousandIndex = (length-i)%3
        if (thousandIndex==0 && i>0) {
            out += ","
        }
        out += str.substring(i,i+1)
    }
    return out
}

// format a float number into a percentage string, with 1 decimal
fun Float.toPercentageString() : String {
    if (this==0f) {
        return ""
    }
    return ((this*10f).roundToInt()/10f).toString() + "%"
}

package com.diegobetancourt.meli.util

import java.text.DecimalFormat
import java.text.NumberFormat

/*
    * File to provides extra funtionality to known classes
 */


// map an Integer to String using currency format
fun Int.toCurrencyString(currency: String = ""): String {
    val formatter: NumberFormat = when (currency) {
        "COP" -> {
            DecimalFormat("$ #,###")
        }
        else -> {
            DecimalFormat("$ #,###")
        }
    }
    return formatter.format(this).replace(",", ".")
}

package com.example.currencyexchangeapp.ui.extantions

import kotlin.math.round

fun Double.countPercent(percent: Double): Double =
    this * percent / 100

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}
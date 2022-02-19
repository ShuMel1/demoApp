package com.example.currencyexchangeapp.data.entity

import androidx.room.Entity
import com.example.currencyexchangeapp.data.BALANCE_TABLE_NAME

@Entity(tableName = BALANCE_TABLE_NAME)
enum class Currency(val code: String) {
    USD("USD"), EUR("EUR"), AMD("AMD");

    companion object {
        fun getCurrency(code: String) =
            when (code) {
                "USD" -> USD
                "EUR" -> EUR
                "AMD" -> AMD
                else -> EUR
            }
    }
}

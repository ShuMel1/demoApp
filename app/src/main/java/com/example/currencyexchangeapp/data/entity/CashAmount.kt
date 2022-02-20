package com.example.currencyexchangeapp.data.entity

import androidx.room.*
import com.example.currencyexchangeapp.data.BALANCE_TABLE_NAME

@Entity(tableName = BALANCE_TABLE_NAME)
data class CashAmount(
    @ColumnInfo(name = "amount")
    var amount: Double,

    @ColumnInfo(name = "currency")
    @PrimaryKey
    @TypeConverters(EnumConverter::class)
    val currency: Currency
)

class EnumConverter {

    @TypeConverter
    fun toCurrency(value: String) = enumValueOf<Currency>(value)

    @TypeConverter
    fun fromCurrency(value: Currency) = value.code
}
package com.example.currencyexchangeapp.data

import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.entity.Currency

//todo move to buildConfig
const val base_url = "http://api.exchangeratesapi.io/v1/"
const val access_key = "d21b3097bdb299eaddfa683174f82aa8"

const val TAG = "CurrencyExchange"

const val BALANCE_TABLE_NAME = "balance_table"

val defaultCashAmounts = mutableListOf(
    CashAmount(1000.0, Currency.EUR),
    CashAmount(0.0, Currency.USD),
    CashAmount(0.0, Currency.AMD))

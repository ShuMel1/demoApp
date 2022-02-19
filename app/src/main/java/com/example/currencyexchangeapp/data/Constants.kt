package com.example.currencyexchangeapp.data

import com.example.currencyexchangeapp.BuildConfig
import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.entity.Currency

const val base_url = BuildConfig.BASE_URL
const val access_key = "fc9659bb7e0531e5e44770563aa72991" // todo need to be updated every day

const val TAG = "CurrencyExchange"

const val BALANCE_TABLE_NAME = "balance_table"

val defaultCashAmounts = mutableListOf(
    CashAmount(1000.0, Currency.EUR),
    CashAmount(0.0, Currency.USD),
    CashAmount(0.0, Currency.AMD))
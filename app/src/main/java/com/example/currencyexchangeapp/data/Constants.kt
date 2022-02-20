package com.example.currencyexchangeapp.data

import com.example.currencyexchangeapp.BuildConfig
import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.entity.Currency

const val base_url = BuildConfig.BASE_URL
const val access_key = "3744d146baee82ec60b36fe92318bd03" // todo need to be updated every day

const val TAG = "CurrencyExchange"

const val BALANCE_TABLE_NAME = "balance_table"
const val DATABASE_NAME = "currency_app_db"
const val PREFS_NAME = "com.example.currencyexchangeapp.data.local_currency_pref"

const val defFreeTransactionsCount = 5
const val commission = 0.7

val defaultCashAmounts = mutableListOf(
    CashAmount(1000.0, Currency.EUR),
    CashAmount(0.0, Currency.USD),
    CashAmount(0.0, Currency.AMD))
package com.example.currencyexchangeapp.data.repository

import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.entity.Currency
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {
    fun getAvailableCurrencies(): Flow<List<Currency>>
    fun getAllCurrencies(): Flow<List<Currency>>
    fun getAllCash(): Flow<List<CashAmount>>
    fun getCashAmount(currency: Currency): Flow<Double>
    suspend fun resetBalanceToDefault()
    suspend fun insertCashAmount(cashAmount: CashAmount)
    suspend fun insertAll(cashAmounts: List<CashAmount>)
    fun getRemainingFreeTransactionsCount():Int
    fun setRemainingFreeTransactionsCount(count: Int)
    fun isInitialBalanceSet(): Boolean
    fun setInitialBalanceSet(b: Boolean)
}
package com.example.currencyexchangeapp.data.local.datasource

import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.entity.Currency
import kotlinx.coroutines.flow.Flow

interface BalanceLocalDataSource {
    fun getAvailableCurrencies(): Flow<List<Currency>>
    suspend fun insertCashAmount(cashAmount: CashAmount)
    suspend fun insertAll(cashAmounts: List<CashAmount>)
    suspend fun deleteBalance()
    fun getAllCurrencies(): Flow<List<Currency>>
    fun getAllCash(): Flow<List<CashAmount>>
    fun getCashAmount(currency: Currency): Flow<Double>
    fun getRemainingFreeTransactionsCount(): Int
    fun setRemainingFreeTransactionsCount(count: Int)
    fun isInitialBalanceSet(): Boolean
    fun setInitialBalanceSet(b: Boolean)
}
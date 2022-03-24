package com.example.currencyexchangeapp.domain.usecase

import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.entity.Currency
import kotlinx.coroutines.flow.Flow


interface MainUseCase {
    fun getAvailableCurrencies(): Flow<List<Currency>>
    fun getAllCurrencies(): Flow<List<Currency>>
    fun getCurrAmount(currency: Currency): Flow<Double>
    suspend fun insertInitialBalance()
    suspend fun updateCash(cashAmount: CashAmount)
    suspend fun updateCashes(cashAmounts: List<CashAmount>)
    fun hasFreeTransactionsCount(): Boolean
    fun setFreeTransactions(count: Int)
    fun decreaseRemainingFreeTransactionsCount()
    fun countCommission(cashAmount: CashAmount): Double
    fun isInitialBalanceSet(): Boolean
    fun setInitialBalanceSet(b: Boolean)
}
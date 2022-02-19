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
}
package com.example.currencyexchangeapp.domain.usecase

import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.entity.Currency
import com.example.currencyexchangeapp.data.repository.BalanceRepository
import kotlinx.coroutines.flow.Flow

class MainUseCaseImpl(private val repository: BalanceRepository): MainUseCase {
    override fun getAvailableCurrencies() =
        repository.getAvailableCurrencies()

    override fun getAllCurrencies(): Flow<List<Currency>> =
        repository.getAllCurrencies()

    override fun getCurrAmount(currency: Currency): Flow<Double> =
        repository.getCashAmount(currency)

    override suspend fun insertInitialBalance() {
        repository.resetBalanceToDefault()
    }

    override suspend fun updateCash(cashAmount: CashAmount) {
        repository.insertCashAmount(cashAmount)
    }
}
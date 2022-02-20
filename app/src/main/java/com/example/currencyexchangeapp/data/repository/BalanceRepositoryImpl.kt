package com.example.currencyexchangeapp.data.repository

import com.example.currencyexchangeapp.data.defaultCashAmounts
import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.entity.Currency
import com.example.currencyexchangeapp.data.local.datasource.BalanceLocalDataSource
import kotlinx.coroutines.flow.Flow

class BalanceRepositoryImpl(private val localDataSource: BalanceLocalDataSource) : BalanceRepository {
    override fun getAvailableCurrencies() =
        localDataSource.getAvailableCurrencies()

    override suspend fun resetBalanceToDefault() {
        localDataSource.deleteBalance()
        localDataSource.insertAll(defaultCashAmounts)
    }

    override suspend fun insertCashAmount(cashAmount: CashAmount) {
        localDataSource.insertCashAmount(cashAmount)
    }

    override suspend fun insertAll(cashAmounts: List<CashAmount>) {
        localDataSource.insertAll(cashAmounts)
    }

    override fun getRemainingFreeTransactionsCount(): Int =
        localDataSource.getRemainingFreeTransactionsCount()

    override fun setRemainingFreeTransactionsCount(count: Int) {
        localDataSource.setRemainingFreeTransactionsCount(count)
    }

    override fun isInitialBalanceSet(): Boolean =
        localDataSource.isInitialBalanceSet()

    override fun setInitialBalanceSet(b: Boolean) {
        localDataSource.setInitialBalanceSet(b)
    }

    override fun getAllCurrencies(): Flow<List<Currency>> =
        localDataSource.getAllCurrencies()

    override fun getAllCash(): Flow<List<CashAmount>> =
        localDataSource.getAllCash()

    override fun getCashAmount(currency: Currency): Flow<Double> =
        localDataSource.getCashAmount(currency)

}
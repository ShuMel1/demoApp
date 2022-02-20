package com.example.currencyexchangeapp.data.local.datasource

import android.content.SharedPreferences
import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.entity.Currency
import com.example.currencyexchangeapp.data.local.room.BalanceDao
import kotlinx.coroutines.flow.Flow

private const val free_transaction_count_key = "free_transaction_count_key"

class BalanceLocalDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
    private val balanceDao: BalanceDao
) : BalanceLocalDataSource {
    override fun getAvailableCurrencies(): Flow<List<Currency>> =
        balanceDao.getAvailableCurrencies()

    override suspend fun insertCashAmount(cashAmount: CashAmount) {
        balanceDao.insertCashAmount(cashAmount)
    }

    override suspend fun insertAll(cashAmounts: List<CashAmount>) {
        balanceDao.insertAll(cashAmounts)
    }

    override suspend fun deleteBalance() {
        balanceDao.deleteAllData()
    }

    override fun getAllCurrencies(): Flow<List<Currency>> =
        balanceDao.getAllCurrencies()

    override fun getAllCash(): Flow<List<CashAmount>> =
        balanceDao.getAllCash()

    override fun getCashAmount(currency: Currency): Flow<Double> =
        balanceDao.getCashAmount(currency)

    override fun getRemainingFreeTransactionsCount(): Int =
        sharedPreferences.getInt(free_transaction_count_key, 0)

    override fun setRemainingFreeTransactionsCount(count: Int) {
        sharedPreferences.edit().putInt(free_transaction_count_key, count).apply()
    }

}
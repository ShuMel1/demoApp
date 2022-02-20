package com.example.currencyexchangeapp.domain.usecase

import com.example.currencyexchangeapp.data.commission
import com.example.currencyexchangeapp.data.defFreeTransactionsCount
import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.entity.Currency
import com.example.currencyexchangeapp.data.repository.BalanceRepository
import com.example.currencyexchangeapp.ui.extantions.countPercent
import com.example.currencyexchangeapp.ui.extantions.round
import kotlinx.coroutines.flow.Flow

class MainUseCaseImpl(private val repository: BalanceRepository) : MainUseCase {
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

    override suspend fun updateCashes(cashAmounts: List<CashAmount>) {
        repository.insertAll(cashAmounts)
    }

    override fun hasFreeTransactionsCount(): Boolean =
        repository.getRemainingFreeTransactionsCount() > 0

    override fun setFreeTransactions(count: Int) {
        repository.setRemainingFreeTransactionsCount(count)
    }

    override fun decreaseRemainingFreeTransactionsCount() {
        repository.setRemainingFreeTransactionsCount(repository.getRemainingFreeTransactionsCount() - 1)
    }

    override fun countCommission(cashAmount: CashAmount): Double =
        cashAmount.amount.countPercent(commission).round(2)

}
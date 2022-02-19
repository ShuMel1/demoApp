package com.example.currencyexchangeapp.domain.usecase

import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.repository.BalanceRepository
import kotlinx.coroutines.flow.Flow

class BalanceUseCaseImpl(private val repository: BalanceRepository): BalanceUseCase {
    override fun getBalance(): Flow<List<CashAmount>> =
        repository.getAllCash()
}
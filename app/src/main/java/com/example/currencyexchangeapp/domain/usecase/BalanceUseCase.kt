package com.example.currencyexchangeapp.domain.usecase

import com.example.currencyexchangeapp.data.entity.CashAmount
import kotlinx.coroutines.flow.Flow

interface BalanceUseCase {
    fun getBalance(): Flow<List<CashAmount>>
}
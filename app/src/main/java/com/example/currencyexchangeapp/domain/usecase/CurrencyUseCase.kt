package com.example.currencyexchangeapp.domain.usecase

import com.example.currencyexchangeapp.data.entity.LatestRatesEntity
import com.example.currencyexchangeapp.data.models.LatestRatesRemoteModel
import com.example.currencyexchangeapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyUseCase {
    fun getLatestRateForSelectedCurr(
        base: String,
        symbol: String
    ): Flow<Resource<LatestRatesEntity>>

}
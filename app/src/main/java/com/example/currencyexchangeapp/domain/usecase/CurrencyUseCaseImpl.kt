package com.example.currencyexchangeapp.domain.usecase

import com.example.currencyexchangeapp.data.entity.LatestRatesEntity
import com.example.currencyexchangeapp.data.models.LatestRatesRemoteModel
import com.example.currencyexchangeapp.data.repository.CurrencyRepository
import com.example.currencyexchangeapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class CurrencyUseCaseImpl(private val repository: CurrencyRepository): CurrencyUseCase {
    override fun getLatestRateForSelectedCurr(
        base: String,
        symbol: String
    ): Flow<Resource<LatestRatesEntity>> =
        repository.getLatestRateForSelectedCurr(base, symbol)


}
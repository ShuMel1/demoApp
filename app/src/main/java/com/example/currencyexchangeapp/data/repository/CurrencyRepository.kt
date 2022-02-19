package com.example.currencyexchangeapp.data.repository

import com.example.currencyexchangeapp.data.entity.LatestRatesEntity
import com.example.currencyexchangeapp.data.models.LatestRatesRemoteModel
import com.example.currencyexchangeapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getLatestRateForSelectedCurr(base: String, selectedCurrency: String): Flow<Resource<LatestRatesEntity>>
}
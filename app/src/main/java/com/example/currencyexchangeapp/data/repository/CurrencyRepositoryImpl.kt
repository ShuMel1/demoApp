package com.example.currencyexchangeapp.data.repository

import com.example.currencyexchangeapp.data.entity.LatestRatesEntity
import com.example.currencyexchangeapp.data.models.LatestRatesRemoteModel
import com.example.currencyexchangeapp.data.remote.datasource.CurrencyRemoteDataSource
import com.example.currencyexchangeapp.domain.utils.RESPONSE_TO_DATA_ENTITY_MAPPER
import com.example.currencyexchangeapp.domain.utils.Resource
import com.example.currencyexchangeapp.domain.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CurrencyRepositoryImpl(private val remoteDataSource: CurrencyRemoteDataSource) : CurrencyRepository {

    override fun getLatestRateForSelectedCurr(
        base: String,
        selectedCurrency: String
    ): Flow<Resource<LatestRatesEntity>> =
        safeApiCall(
            mapper = RESPONSE_TO_DATA_ENTITY_MAPPER,
            apiCall = { remoteDataSource.getLatestRateForSelectedCurrOnly(base, selectedCurrency) },
            convertingKey = selectedCurrency
        )

}
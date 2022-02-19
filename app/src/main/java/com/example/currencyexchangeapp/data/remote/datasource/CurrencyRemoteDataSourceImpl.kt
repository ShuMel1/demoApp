package com.example.currencyexchangeapp.data.remote.datasource

import com.example.currencyexchangeapp.data.models.LatestRatesRemoteModel
import com.example.currencyexchangeapp.data.remote.ApiClient
import retrofit2.Response

class CurrencyRemoteDataSourceImpl(private val apiClient: ApiClient) : CurrencyRemoteDataSource {
    override suspend fun getLatestRates() =
        apiClient.getLatestRates()

    override suspend fun getLatestRateForSelectedCurrOnly(
        base: String,
        symbol: String
    ): Response<LatestRatesRemoteModel> =
        apiClient.getLatestRateForSelectedCurrency(base = base, symbol = symbol)

}
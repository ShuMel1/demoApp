package com.example.currencyexchangeapp.data.remote.datasource

import com.example.currencyexchangeapp.data.models.LatestRatesRemoteModel
import com.google.gson.JsonObject
import retrofit2.Response

interface CurrencyRemoteDataSource {
    suspend fun getLatestRates() : Response<JsonObject>

    suspend fun getLatestRateForSelectedCurrOnly(base: String, symbol: String) : Response<LatestRatesRemoteModel>
}
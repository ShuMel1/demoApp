package com.example.currencyexchangeapp.data.remote

import com.example.currencyexchangeapp.data.access_key
import com.example.currencyexchangeapp.data.models.LatestRatesRemoteModel
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("latest")
    suspend fun getLatestRates(
        @Query("access_key") accessKey: String = access_key
    ): Response<JsonObject>

    @GET("latest")
    suspend fun getLatestRateForSelectedCurrency(
        @Query("access_key") accessKey: String = access_key,
        @Query("base") base:String,
        @Query("symbols") symbol:String
    ): Response<LatestRatesRemoteModel>
}
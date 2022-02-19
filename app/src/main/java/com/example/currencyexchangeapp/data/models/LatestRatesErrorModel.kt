package com.example.currencyexchangeapp.data.models

import com.google.gson.annotations.SerializedName

data class LatestRatesErrorModel(
    @SerializedName("error")
    val error: LatestErrorModel
)

data class LatestErrorModel(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String
)

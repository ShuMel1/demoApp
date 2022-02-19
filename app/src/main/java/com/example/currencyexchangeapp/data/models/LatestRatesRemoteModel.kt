package com.example.currencyexchangeapp.data.models

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class LatestRatesRemoteModel(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("rates")
    val rates: JsonObject,
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String
)

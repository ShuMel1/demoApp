package com.example.currencyexchangeapp.domain.utils

import com.example.currencyexchangeapp.data.entity.LatestRatesEntity

sealed class Event<out DATA>(
    val messageText: String = "",
    val data: DATA? = null
) {
    class LatestRatesEntitySuccessEvent(
        data: LatestRatesEntity
    ) : Event<LatestRatesEntity>(data = data)

    class LatestRatesEntityErrorEvent(
        messageText: String
    ) : Event<LatestRatesEntity>(messageText = messageText)
}
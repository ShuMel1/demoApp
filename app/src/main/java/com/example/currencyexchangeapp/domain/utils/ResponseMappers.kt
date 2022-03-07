package com.example.currencyexchangeapp.domain.utils

import com.example.currencyexchangeapp.data.entity.LatestRatesEntity
import com.example.currencyexchangeapp.data.models.LatestRatesRemoteModel

val RESPONSE_TO_DATA_ENTITY_MAPPER = object : Mapper<LatestRatesRemoteModel, LatestRatesEntity> {
    override fun map(s: LatestRatesRemoteModel, key: String): LatestRatesEntity {
        return LatestRatesEntity(
            base = s.base,
            rateForSelected = s.rates.get(key).asDouble
        )
    }
}
package com.example.currencyexchangeapp.domain.utils

interface Mapper<SOURCE, RESULT> {
    fun map(s: SOURCE, key: String): RESULT
}
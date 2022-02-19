package com.example.currencyexchangeapp.data.repository.di

import com.example.currencyexchangeapp.data.repository.CurrencyRepository
import com.example.currencyexchangeapp.data.repository.CurrencyRepositoryImpl
import com.example.currencyexchangeapp.data.repository.BalanceRepository
import com.example.currencyexchangeapp.data.repository.BalanceRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<CurrencyRepository> {
        CurrencyRepositoryImpl(remoteDataSource = get())
    }
    factory<BalanceRepository> {
        BalanceRepositoryImpl(localDataSource = get())
    }
}
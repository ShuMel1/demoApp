package com.example.currencyexchangeapp.domain.di

import com.example.currencyexchangeapp.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    factory<CurrencyUseCase> {
        CurrencyUseCaseImpl(repository = get())
    }
    factory<MainUseCase> {
        MainUseCaseImpl(repository = get())
    }
    factory<BalanceUseCase> {
        BalanceUseCaseImpl(repository = get())
    }
}
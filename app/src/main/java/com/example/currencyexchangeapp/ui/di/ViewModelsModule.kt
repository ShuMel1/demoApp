package com.example.currencyexchangeapp.ui.di

import com.example.currencyexchangeapp.ui.feature.balance.BalanceViewModel
import com.example.currencyexchangeapp.ui.feature.change.CurrencyExchangeViewModel
import com.example.currencyexchangeapp.ui.feature.main.MainSharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        CurrencyExchangeViewModel(useCase = get(), mainUseCase = get())
    }
    viewModel {
        MainSharedViewModel(useCase = get())
    }
    viewModel {
        BalanceViewModel(useCase = get())
    }
}
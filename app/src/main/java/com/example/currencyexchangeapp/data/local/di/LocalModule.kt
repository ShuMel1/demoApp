package com.example.currencyexchangeapp.data.local.di

import androidx.room.Room
import com.example.currencyexchangeapp.data.local.AppDatabase
import com.example.currencyexchangeapp.data.local.datasource.BalanceLocalDataSource
import com.example.currencyexchangeapp.data.local.datasource.BalanceLocalDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val DATABASE_NAME = "currency_app_db"
val localModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().balanceDao()
    }

    single<BalanceLocalDataSource> {
        BalanceLocalDataSourceImpl(
            balanceDao = get()
        )
    }


}
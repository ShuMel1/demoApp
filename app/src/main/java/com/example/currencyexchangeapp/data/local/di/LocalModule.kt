package com.example.currencyexchangeapp.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.currencyexchangeapp.data.DATABASE_NAME
import com.example.currencyexchangeapp.data.PREFS_NAME
import com.example.currencyexchangeapp.data.local.AppDatabase
import com.example.currencyexchangeapp.data.local.datasource.BalanceLocalDataSource
import com.example.currencyexchangeapp.data.local.datasource.BalanceLocalDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {

    single {
        androidContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

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
            sharedPreferences = get(),
            balanceDao = get()
        )
    }


}
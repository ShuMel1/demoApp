package com.example.currencyexchangeapp.ui

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.currencyexchangeapp.data.base_url
import com.example.currencyexchangeapp.data.local.di.localModule
import com.example.currencyexchangeapp.data.remote.di.remoteModule
import com.example.currencyexchangeapp.data.repository.di.repositoryModule
import com.example.currencyexchangeapp.domain.di.domainModule
import com.example.currencyexchangeapp.ui.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    remoteModule(base_url),
                    repositoryModule,
                    localModule,
                    domainModule,
                    viewModelsModule
                )
            )
        }
    }
}
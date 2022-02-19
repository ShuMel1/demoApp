package com.example.currencyexchangeapp.data.remote.di

import android.annotation.SuppressLint
import com.example.currencyexchangeapp.data.remote.ApiClient
import com.example.currencyexchangeapp.data.remote.datasource.CurrencyRemoteDataSource
import com.example.currencyexchangeapp.data.remote.datasource.CurrencyRemoteDataSourceImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun remoteModule(baseUrl: String) = module {

    factory<Interceptor> {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    factory<OkHttpClient> {
        return@factory try {
            OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(get<Interceptor>())
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }


    single {
        Retrofit.Builder().client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    factory {
        get<Retrofit>().create(ApiClient::class.java)
    }
    single<CurrencyRemoteDataSource> {
        CurrencyRemoteDataSourceImpl(apiClient = get())
    }
}

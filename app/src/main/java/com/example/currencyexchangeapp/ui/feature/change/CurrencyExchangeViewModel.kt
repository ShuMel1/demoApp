package com.example.currencyexchangeapp.ui.feature.change

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.currencyexchangeapp.data.TAG
import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.entity.Currency
import com.example.currencyexchangeapp.data.entity.LatestRatesEntity
import com.example.currencyexchangeapp.domain.usecase.CurrencyUseCase
import com.example.currencyexchangeapp.domain.usecase.MainUseCase
import com.example.currencyexchangeapp.domain.utils.Resource
import com.example.currencyexchangeapp.ui.common.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

const val def_sell_curr = "EUR"
const val def_receive_curr = "USD"

class CurrencyExchangeViewModel(private val useCase: CurrencyUseCase , private val mainUseCase: MainUseCase) : BaseViewModel() {
    val currencyLiveData = MutableLiveData<LatestRatesEntity>() // todo change with events
    val selectedSellCurr = MutableLiveData<String>()
    val selectedReceiveCurr = MutableLiveData<String>()
    val userCurrencies = MutableLiveData<List<Currency>>()
    val allCurrencies = MutableLiveData<List<Currency>>()
    val baseCurrAmount = MutableLiveData<Double>()

    fun getLatestRateForSelectedCurrency() =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                while (isActive) {
                    useCase.getLatestRateForSelectedCurr(
                        selectedSellCurr.value ?: def_sell_curr,
                        selectedReceiveCurr.value ?: def_receive_curr
                    ).catch {
                        Log.d(TAG, "getLatestRateForSelectedCurr error message: ${it.message}")
                    }.flowOn(Dispatchers.IO).onEach {
                        when (it) {
                            is Resource.Error -> {
                                // todo
                            }
                            is Resource.Success -> {
                                currencyLiveData.postValue(it.data!!)
                            }
                            is Resource.Loading -> {
                                // todo
                            }
                        }
                    }.flowOn(Dispatchers.Main).launchIn(viewModelScope)
                    delay(5000)
                }
            }
        }

    fun getSellCurrencies() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                mainUseCase.getAvailableCurrencies().catch {
                    Log.d(TAG, "getAvailableCurrencies error message: ${it.message}")
                }.flowOn(Dispatchers.IO).onEach {
                    userCurrencies.postValue(it)
                }.flowOn(Dispatchers.Main).launchIn(viewModelScope)
            }
        }
    }

    fun getReceiveCurrencies() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                mainUseCase.getAllCurrencies().catch {
                    Log.d(TAG, "getAllCurrencies error message: ${it.message}")
                }.flowOn(Dispatchers.IO).onEach {
                    allCurrencies.postValue(it)
                }.flowOn(Dispatchers.Main).launchIn(viewModelScope)
            }
        }
    }

    fun getBaseCurrAmount() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                mainUseCase.getCurrAmount(Currency.getCurrency(selectedSellCurr.value ?: def_sell_curr))
                    .catch {
                        Log.d(TAG, "getCurrAmount error message: ${it.message}")
                    }.flowOn(Dispatchers.IO).onEach {
                        baseCurrAmount.postValue(it)
                    }.flowOn(Dispatchers.Main).launchIn(viewModelScope)
            }
        }
    }

    fun insertInitialValues() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                mainUseCase.insertInitialBalance() // todo update
                getSellCurrencies()
            }
        }

    }

    fun submitSale(sellCashAmount: CashAmount, receiveCashAmount: CashAmount) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mainUseCase.updateCash(sellCashAmount)
                mainUseCase.updateCash(receiveCashAmount)
            }
        }
    }

}
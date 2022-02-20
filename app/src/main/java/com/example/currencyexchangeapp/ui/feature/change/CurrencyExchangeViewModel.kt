package com.example.currencyexchangeapp.ui.feature.change

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.currencyexchangeapp.data.TAG
import com.example.currencyexchangeapp.data.commission
import com.example.currencyexchangeapp.data.defFreeTransactionsCount
import com.example.currencyexchangeapp.data.defaultCashAmounts
import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.entity.Currency
import com.example.currencyexchangeapp.domain.usecase.CurrencyUseCase
import com.example.currencyexchangeapp.domain.usecase.MainUseCase
import com.example.currencyexchangeapp.domain.utils.Event
import com.example.currencyexchangeapp.domain.utils.Resource
import com.example.currencyexchangeapp.ui.common.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

const val def_sell_curr = "EUR"
const val def_receive_curr = "USD"

class CurrencyExchangeViewModel(
    private val useCase: CurrencyUseCase,
    private val mainUseCase: MainUseCase
) : BaseViewModel() {
    val currencyLiveData = MutableLiveData<Event<*>>()
    val selectedSellCurr = MutableLiveData<String>().apply { postValue(def_sell_curr) }
    val selectedReceiveCurr = MutableLiveData<String>().apply { postValue(def_receive_curr) }
    val userCurrencies = MutableLiveData<List<Currency>>()
    val allCurrencies = MutableLiveData<List<Currency>>()
    val baseCurrAmount = MutableLiveData<Double>()
    val commissionFee = MutableLiveData<Pair<Double, String>>()
    val receiveCurrAmount = MutableLiveData<Double>().apply { postValue(0.0) }

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
                                currencyLiveData.postValue(Event.LatestRatesEntityErrorEvent(it.exception.message!!))
                            }
                            is Resource.Success -> {
                                currencyLiveData.postValue(Event.LatestRatesEntitySuccessEvent(it.data!!))
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
                mainUseCase.getCurrAmount(
                    Currency.getCurrency(
                        selectedSellCurr.value ?: def_sell_curr
                    )
                )
                    .catch {
                        Log.d(TAG, "getCurrAmount error message: ${it.message}")
                    }.flowOn(Dispatchers.IO).onEach {
                        baseCurrAmount.postValue(it)
                    }.flowOn(Dispatchers.Main).launchIn(viewModelScope)
            }
        }
    }

    fun getReceiveCurrAmount() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                mainUseCase.getCurrAmount(
                    Currency.getCurrency(
                        selectedReceiveCurr.value ?: def_receive_curr
                    )
                )
                    .catch {
                        Log.d(TAG, "getCurrAmount error message: ${it.message}")
                    }.flowOn(Dispatchers.IO).onEach {
                        receiveCurrAmount.postValue(it)
                    }.flowOn(Dispatchers.Main).launchIn(viewModelScope)
            }
        }
    }

    fun insertInitialValues() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                mainUseCase.updateCashes(defaultCashAmounts)
                mainUseCase.setFreeTransactions(defFreeTransactionsCount)
                getSellCurrencies()
            }
        }

    }

    fun submitSale(sellCashAmount: CashAmount, receiveCashAmount: CashAmount) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (mainUseCase.hasFreeTransactionsCount()) {
                    mainUseCase.updateCashes(listOf(sellCashAmount, receiveCashAmount))
                    commissionFee.postValue(Pair(0.0, receiveCashAmount.currency.code))
                    mainUseCase.decreaseRemainingFreeTransactionsCount()
                } else {
                    val commissionAmount = mainUseCase.countCommission(receiveCashAmount)
                    receiveCashAmount.amount = receiveCashAmount.amount - commissionAmount
                    mainUseCase.updateCashes(listOf(sellCashAmount, receiveCashAmount))
                    commissionFee.postValue(Pair(commissionAmount, receiveCashAmount.currency.code))
                }
            }
        }
    }

}
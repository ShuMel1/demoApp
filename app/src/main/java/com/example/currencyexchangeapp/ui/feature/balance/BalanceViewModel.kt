package com.example.currencyexchangeapp.ui.feature.balance

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.currencyexchangeapp.data.TAG
import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.domain.usecase.BalanceUseCase
import com.example.currencyexchangeapp.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BalanceViewModel(private val useCase: BalanceUseCase) : BaseViewModel() {

    val allCashes = MutableLiveData<List<CashAmount>>()

    fun getBalance() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            useCase.getBalance().catch {
                Log.d(TAG, "getBalance error message: ${it.message}")
            }.flowOn(Dispatchers.IO).onEach {
                allCashes.postValue(it)
            }.flowOn(Dispatchers.Main).launchIn(viewModelScope)
        }
    }
}
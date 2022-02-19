package com.example.currencyexchangeapp.ui.feature.change

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MutableLiveData
import com.example.currencyexchangeapp.R
import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.entity.Currency
import com.example.currencyexchangeapp.databinding.CurrencyChangeFragmentBinding
import com.example.currencyexchangeapp.ui.common.BaseFragment
import com.example.currencyexchangeapp.ui.extantions.viewBinding
import com.example.currencyexchangeapp.ui.feature.main.MainSharedViewModel
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val def_amount = 1000

class CurrencyExchangeFragment : BaseFragment(R.layout.currency_change_fragment) {

    private lateinit var repeatableJob: Job
    override val viewModel: CurrencyExchangeViewModel by viewModel()
    private val sharedViewModel: MainSharedViewModel by sharedViewModel()
    private val viewBinding: CurrencyChangeFragmentBinding by viewBinding()

    override fun initViews() {
        super.initViews()
        viewModel.insertInitialValues()
        viewModel.getSellCurrencies()
        viewModel.getReceiveCurrencies()
        viewModel.getBaseCurrAmount()
        repeatableJob = viewModel.getLatestRateForSelectedCurrency()

        initSpinnerAdapter(listOf(def_sell_curr), viewBinding.sellCurrencySp)
        initSpinnerAdapter(emptyList(), viewBinding.receiveCurrencySp)
    }

    private fun initSpinnerAdapter(
        list: List<String>,
        spinner: Spinner
    ) {
        val newAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            list
        )
        newAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(spinner) {
            adapter = newAdapter
            setSelection(0)
        }
    }

    override fun bindData() {
        super.bindData()
        viewModel.currencyLiveData.observe(viewLifecycleOwner) {
            viewBinding.currencyRate.text = it.rateForSelected.toString()
        }
        viewModel.selectedReceiveCurr.observe(viewLifecycleOwner) {
            repeatableJob.cancel()
            repeatableJob = viewModel.getLatestRateForSelectedCurrency()
        }
        viewModel.selectedSellCurr.observe(viewLifecycleOwner) {
            repeatableJob.cancel()
            repeatableJob = viewModel.getLatestRateForSelectedCurrency()
        }
        viewModel.userCurrencies.observe(viewLifecycleOwner) { currencies ->
//            initSpinnerAdapter(currencies.map { it.code }, viewBinding.sellCurrencySp)  // todo The API you provided does not allow to change base currency in free subscription plan
        }
        viewModel.allCurrencies.observe(viewLifecycleOwner) { currencies ->
            initSpinnerAdapter(currencies.map { it.code }, viewBinding.receiveCurrencySp)
        }
    }

    override fun initListeners() {
        super.initListeners()
        with(viewBinding) {
            submitBtn.setOnClickListener {
                if (hasNoEmptyFields() && isCashAvailable()) { //todo clear edit texts after submitting
                    val sellCashAmount = CashAmount(
                        getExpectedRemainingBalance() ,
                        Currency.getCurrency(sellCurrencySp.selectedItem.toString())
                    )
                    val receiveCashAmount = CashAmount(
                        receiveEdit.text.toString().toDouble(),
                        Currency.getCurrency(receiveCurrencySp.selectedItem.toString())
                    )
                    viewModel.submitSale(sellCashAmount, receiveCashAmount)
                    clearFields()
                }
            }
//            sellCurrencySp.onItemSelectedListener =
//                onItemSelectedListener(viewModel.selectedSellCurr)   // todo The API you provided does not allow to change base currency in free subscription plan
            sellCurrencySp.isEnabled = false
            receiveCurrencySp.onItemSelectedListener =
                onItemSelectedListener(viewModel.selectedReceiveCurr)
            sellEdit.doOnTextChanged { text, start, before, count ->
                text?.let {
                    if (text.isNotEmpty()) {
                        viewModel.currencyLiveData.value?.let {
                            val amount: Double = it.rateForSelected * text.toString().toDouble()
                            receiveEdit.setText(amount.toString())
                        }
                    }
                }

            }
        }
    }

    //todo update receiveEdit after receiveSp change

    private fun clearFields() {
        with(viewBinding){
            sellEdit.setText("")
            receiveEdit.setText("")
        }
    }

    private fun isCashAvailable(): Boolean {
        return  getExpectedRemainingBalance() > 0
    }

    private fun getExpectedRemainingBalance() =
        viewModel.baseCurrAmount.value!! - viewBinding.sellEdit.text.toString().toDouble()

    private fun hasNoEmptyFields(): Boolean = viewBinding.sellEdit.text?.isNotEmpty() ?: false
            && viewBinding.receiveEdit.text?.isNotEmpty() ?: false

    private fun onItemSelectedListener(selectedCurrency: MutableLiveData<String>) =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                selectedCurrency.postValue(parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
}

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
import com.example.currencyexchangeapp.data.entity.LatestRatesEntity
import com.example.currencyexchangeapp.databinding.CurrencyChangeFragmentBinding
import com.example.currencyexchangeapp.domain.utils.Event
import com.example.currencyexchangeapp.ui.common.BaseFragment
import com.example.currencyexchangeapp.ui.dialog.AppDialogFragment
import com.example.currencyexchangeapp.ui.extantions.round
import com.example.currencyexchangeapp.ui.extantions.viewBinding
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyExchangeFragment : BaseFragment(R.layout.currency_change_fragment) {

    private lateinit var repeatableJob: Job
    override val viewModel: CurrencyExchangeViewModel by viewModel()
    private val viewBinding: CurrencyChangeFragmentBinding by viewBinding()
    private var latestRate: LatestRatesEntity? = null
    private lateinit var dialog: AppDialogFragment

    override fun initViews() {
        super.initViews()
        viewModel.insertInitialValues()
        viewModel.getSellCurrencies()
        viewModel.getReceiveCurrencies()
        viewModel.getBaseCurrAmount()

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
        viewModel.commissionFee.observe(viewLifecycleOwner) { commission ->
            showSuccessMessage(commission.first.toString() + " " + commission.second)
            clearFields()
        }
        viewModel.currencyLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Event.LatestRatesEntityErrorEvent -> {
                    if (::repeatableJob.isInitialized) repeatableJob.cancel()
                    showDialog(getString(R.string.error), it.messageText)
                }
                is Event.LatestRatesEntitySuccessEvent -> {
                    latestRate = it.data
                    viewBinding.currencyRate.text = latestRate?.rateForSelected?.round(2).toString()
                    updateReceiveAmount()
                }
            }
        }
        viewModel.selectedReceiveCurr.observe(viewLifecycleOwner) {
            if (::repeatableJob.isInitialized) repeatableJob.cancel()
            repeatableJob = viewModel.getLatestRateForSelectedCurrency()
            viewModel.getReceiveCurrAmount()
        }
        viewModel.selectedSellCurr.observe(viewLifecycleOwner) {
            // The API restriction in free subscription plan
//            if (::repeatableJob.isInitialized) repeatableJob.cancel()
//            repeatableJob = viewModel.getLatestRateForSelectedCurrency()
        }
        viewModel.userCurrencies.observe(viewLifecycleOwner) {
            // The API restriction in free subscription plan
//            initSpinnerAdapter(currencies.map { it.code }, viewBinding.sellCurrencySp)
        }
        viewModel.allCurrencies.observe(viewLifecycleOwner) { currencies ->
            initSpinnerAdapter(currencies.map { it.code }, viewBinding.receiveCurrencySp)
        }
    }

    override fun initListeners() {
        super.initListeners()
        with(viewBinding) {
            submitBtn.setOnClickListener {
                if (hasNoEmptyFields()) {
                    if (isNotTheSameCurrency()) {
                        if (isCashAvailable()) {
                            val sellCashAmount = CashAmount(
                                getExpectedRemainingBalance(),
                                Currency.getCurrency(sellCurrencySp.selectedItem.toString())
                            )
                            val receiveCashAmount = CashAmount(
                                getExpectedBecomingBalance(),
                                Currency.getCurrency(receiveCurrencySp.selectedItem.toString())
                            )
                            viewModel.submitSale(sellCashAmount, receiveCashAmount)
                        } else showDialog(
                            getString(R.string.error),
                            getString(R.string.invalid_amount)
                        )
                    } else showDialog(
                        getString(R.string.error),
                        getString(R.string.same_currency_error)
                    )
                } else showDialog(getString(R.string.error), getString(R.string.empty_field))
            }
            // The API restriction in free subscription plan
//            sellCurrencySp.onItemSelectedListener =
//                onItemSelectedListener(viewModel.selectedSellCurr)

            sellCurrencySp.isEnabled = false
            receiveCurrencySp.onItemSelectedListener =
                onItemSelectedListener(viewModel.selectedReceiveCurr)
            sellEdit.doOnTextChanged { text, _, _, _ ->
                text?.let {
                    if (text.isNotEmpty()) {
                        latestRate?.let {
                            val amount: Double =
                                it.rateForSelected * text.toString().toDouble()
                            receiveEdit.setText(amount.round(2).toString())
                        }
                    } else receiveEdit.setText("")
                }
            }
        }
    }


    private fun showSuccessMessage(commission: String) {
        with(viewBinding) {
            val sellStr = sellEdit.text.toString() + " " + sellCurrencySp.selectedItem.toString()
            val receiveStr =
                receiveEdit.text.toString() + " " + receiveCurrencySp.selectedItem.toString()
            showDialog(
                getString(R.string.success_title),
                getString(R.string.success_message, sellStr, receiveStr, commission)
            )
        }
    }

    private fun showDialog(title: String, message: String) {
        if (::dialog.isInitialized) dialog.dismiss()
        dialog = AppDialogFragment.newInstance(title, message)
        dialog.show(childFragmentManager, AppDialogFragment.TAG)
    }

    private fun isNotTheSameCurrency(): Boolean =
        viewModel.selectedSellCurr.value != viewModel.selectedReceiveCurr.value

    private fun clearFields() {
        with(viewBinding) {
            sellEdit.setText("")
            receiveEdit.setText("")
        }
    }

    private fun isCashAvailable(): Boolean {
        return getExpectedRemainingBalance() >= 0
    }

    private fun getExpectedBecomingBalance() =
        viewBinding.receiveEdit.text.toString().toDouble() + viewModel.receiveCurrAmount.value!!

    private fun getExpectedRemainingBalance() =
        viewModel.baseCurrAmount.value!! - viewBinding.sellEdit.text.toString().toDouble()

    private fun hasNoEmptyFields(): Boolean = viewBinding.sellEdit.text?.isNotEmpty() ?: false
            && viewBinding.receiveEdit.text?.isNotEmpty() ?: false && viewBinding.sellEdit.text?.toString()
        ?.toDouble() != 0.0

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

    private fun updateReceiveAmount() {
        with(viewBinding) {
            sellEdit.text.toString().let { text ->
                if (text.isNotEmpty()) {
                    latestRate?.let {
                        val amount: Double = it.rateForSelected * text.toDouble()
                        receiveEdit.setText(amount.round(2).toString())
                    }
                } else receiveEdit.setText("")
            }
        }
    }
}

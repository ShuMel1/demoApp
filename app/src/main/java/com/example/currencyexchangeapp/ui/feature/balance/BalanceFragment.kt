package com.example.currencyexchangeapp.ui.feature.balance

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangeapp.R
import com.example.currencyexchangeapp.databinding.BalanceFragmentBinding
import com.example.currencyexchangeapp.ui.adapter.BalanceRvAdapter
import com.example.currencyexchangeapp.ui.common.BaseFragment
import com.example.currencyexchangeapp.ui.extantions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BalanceFragment : BaseFragment(R.layout.balance_fragment) {

    override val viewModel: BalanceViewModel by viewModel()
    private val viewBinding: BalanceFragmentBinding by viewBinding()
    private lateinit var balanceRvAdapter: BalanceRvAdapter

    override fun initViews() {
        super.initViews()
        viewBinding.balanceRv.apply {
            balanceRvAdapter = BalanceRvAdapter()
            adapter = balanceRvAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
        viewModel.getBalance()
    }

    override fun bindData() {
        super.bindData()
        viewModel.allCashes.observe(viewLifecycleOwner) {
            balanceRvAdapter.submitList(it)
        }
    }

}
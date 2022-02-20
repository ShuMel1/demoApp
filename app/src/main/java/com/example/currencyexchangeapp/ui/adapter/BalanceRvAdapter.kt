package com.example.currencyexchangeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.databinding.ItemBalanceListBinding
import com.example.currencyexchangeapp.ui.extantions.round

class BalanceRvAdapter :
    ListAdapter<CashAmount, BalanceRvAdapter.BalanceViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder =
        BalanceViewHolder(
            ItemBalanceListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }

    inner class BalanceViewHolder(private val binding: ItemBalanceListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cashAmount: CashAmount) {
            binding.apply {
                cashAmountTxt.text = cashAmount.amount.round(2).toString()
                cashNameTxt.text = cashAmount.currency.code
            }
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<CashAmount>() {
        override fun areItemsTheSame(
            oldItem: CashAmount,
            newItem: CashAmount
        ): Boolean = oldItem.currency == newItem.currency

        override fun areContentsTheSame(
            oldItem: CashAmount,
            newItem: CashAmount
        ): Boolean = oldItem.amount == newItem.amount
    }
}
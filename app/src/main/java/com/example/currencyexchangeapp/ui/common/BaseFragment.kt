package com.example.currencyexchangeapp.ui.common
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment(layoutId: Int): Fragment(layoutId) {

    protected abstract val viewModel:  BaseViewModel
    protected open fun initViews() = Unit
    protected open fun bindData() = Unit
    protected open fun initListeners() = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindData()
        initListeners()
    }

}
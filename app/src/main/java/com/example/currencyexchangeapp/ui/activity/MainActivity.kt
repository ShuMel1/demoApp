package com.example.currencyexchangeapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bluelinelabs.conductor.Conductor.attachRouter
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.example.currencyexchangeapp.databinding.ActivityMainBinding
import com.example.currencyexchangeapp.ui.controller.MainController

class MainActivity : AppCompatActivity() {

    private lateinit var router: Router
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        router = attachRouter(this, binding.controllerContainer, savedInstanceState)
            .setPopRootControllerMode(Router.PopRootControllerMode.NEVER)

        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(MainController()))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}
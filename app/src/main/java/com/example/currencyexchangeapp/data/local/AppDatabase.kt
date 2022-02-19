package com.example.currencyexchangeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.local.room.BalanceDao

@Database(entities = [
    CashAmount::class
], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun balanceDao(): BalanceDao
}
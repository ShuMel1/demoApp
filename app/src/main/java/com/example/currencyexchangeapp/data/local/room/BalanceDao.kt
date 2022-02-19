package com.example.currencyexchangeapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyexchangeapp.data.entity.CashAmount
import com.example.currencyexchangeapp.data.BALANCE_TABLE_NAME
import com.example.currencyexchangeapp.data.entity.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {

    @Query("SELECT * FROM $BALANCE_TABLE_NAME where amount > 0")
    fun getAllCash(): Flow<List<CashAmount>>

    @Query("SELECT amount FROM $BALANCE_TABLE_NAME where currency = :currency")
    fun getCashAmount(currency: Currency): Flow<Double>

    @Query("SELECT currency FROM $BALANCE_TABLE_NAME")
    fun getAllCurrencies(): Flow<List<Currency>>

    @Query("SELECT currency FROM $BALANCE_TABLE_NAME where amount > 0")
    fun getAvailableCurrencies(): Flow<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCashAmount(cashAmount: CashAmount)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cashAmounts: List<CashAmount>)

    @Query("DELETE FROM $BALANCE_TABLE_NAME")
    suspend fun deleteAllData()
}
package eunix56.example.com.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import eunix56.example.com.currencyconverter.data.db.entity.CurrencyExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.LastNumOfDaysExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem
import eunix56.example.com.currencyconverter.internal.utils.DataResult

interface CurrencyConverterRepository {
    suspend fun getCurrencyExchangeRate(): LiveData<DataResult<CurrencyExchangeRate>>

    suspend fun getHistoryExchangeRate(startAt: String, endAt: String): LiveData<DataResult<LastNumOfDaysExchangeRate>>
}
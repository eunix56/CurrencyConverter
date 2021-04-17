package eunix56.example.com.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import eunix56.example.com.currencyconverter.data.db.entity.CurrencyExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem

interface CurrencyConverterRepository {
    suspend fun getCurrencyExchangeRate(): LiveData<CurrencyExchangeRate>
}
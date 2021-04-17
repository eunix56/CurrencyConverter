package eunix56.example.com.currencyconverter.data.network

import androidx.lifecycle.LiveData
import eunix56.example.com.currencyconverter.data.db.entity.CurrencyExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem

interface CurrencyNetworkDataSource {
    val downloadedCurrencyRates: LiveData<CurrencyExchangeRate>

    val downloadedCurrencyItem: LiveData<List<CurrencyItem>>

    suspend fun fetchCurrencyRates()

    fun fetchCurrencyItems()

}
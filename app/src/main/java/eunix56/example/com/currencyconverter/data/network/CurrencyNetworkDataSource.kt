package eunix56.example.com.currencyconverter.data.network

import androidx.lifecycle.LiveData
import eunix56.example.com.currencyconverter.data.db.entity.CurrencyExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.LastNumOfDaysExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem
import eunix56.example.com.currencyconverter.internal.utils.DataResult

interface CurrencyNetworkDataSource {

    suspend fun fetchCurrencyRates(): DataResult<CurrencyExchangeRate>

    suspend fun fetchHistoryExchangeRate(startAt: String, endAt: String): DataResult<LastNumOfDaysExchangeRate>

}
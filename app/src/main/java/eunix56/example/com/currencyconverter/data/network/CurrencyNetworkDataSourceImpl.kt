package eunix56.example.com.currencyconverter.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import eunix56.example.com.currencyconverter.internal.NoConnectivityException
import eunix56.example.com.currencyconverter.data.db.entity.CurrencyExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.LastNumOfDaysExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem
import eunix56.example.com.currencyconverter.data.provider.CurrencyItemProvider
import eunix56.example.com.currencyconverter.data.provider.CurrencyItemProviderImpl
import eunix56.example.com.currencyconverter.internal.utils.DataResult

class CurrencyNetworkDataSourceImpl(
    private val exchangeRateApiService: ExchangeRateApiService
) : CurrencyNetworkDataSource {

    override suspend fun fetchCurrencyRates(): DataResult<CurrencyExchangeRate> {
        return try {
            val fetchedCurrencyRates =
                exchangeRateApiService
                    .getLatestExchangeRateAsync()
                    .await()
            DataResult.success(fetchedCurrencyRates)
        } catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
            DataResult.error(e.message.toString())
        }
    }

    override suspend fun fetchHistoryExchangeRate(startAt: String, endAt: String): DataResult<LastNumOfDaysExchangeRate> {
        return try {
            val fetchedHistoryRates =
                exchangeRateApiService
                    .getLastNumOfDaysExchangeRateAsync(startAt, endAt)
                    .await()
            DataResult.success(fetchedHistoryRates)
        } catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
            DataResult.error(e.message.toString())
        }
    }
}
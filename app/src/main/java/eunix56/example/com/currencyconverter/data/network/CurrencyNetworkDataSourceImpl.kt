package eunix56.example.com.currencyconverter.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import eunix56.example.com.currencyconverter.internal.NoConnectivityException
import eunix56.example.com.currencyconverter.data.db.entity.CurrencyExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem
import eunix56.example.com.currencyconverter.data.provider.CurrencyItemProvider
import eunix56.example.com.currencyconverter.data.provider.CurrencyItemProviderImpl

class CurrencyNetworkDataSourceImpl(
    private val exchangeRateApiService: ExchangeRateApiService
) : CurrencyNetworkDataSource {

    private val _downloadedCurrencyRates = MutableLiveData<CurrencyExchangeRate>()

    private val _downloadedCurrencyItem = MutableLiveData<List<CurrencyItem>>()

    override val downloadedCurrencyRates: LiveData<CurrencyExchangeRate>
        get() = _downloadedCurrencyRates

    override val downloadedCurrencyItem: LiveData<List<CurrencyItem>>
        get() = _downloadedCurrencyItem

    override suspend fun fetchCurrencyRates() {
        try {
            val fetchedCurrencyRates =
                exchangeRateApiService
                .getLatestExchangeRateAsync()
                    .await()
            _downloadedCurrencyRates.postValue(fetchedCurrencyRates)
        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
        }
    }

    override fun fetchCurrencyItems() {
        val fetchedCurrencyItems = CurrencyItemProviderImpl().fetchCurrencyItems()
        _downloadedCurrencyItem.postValue(fetchedCurrencyItems)
    }
}
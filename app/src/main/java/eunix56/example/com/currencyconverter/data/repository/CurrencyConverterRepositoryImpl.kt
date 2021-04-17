package eunix56.example.com.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import eunix56.example.com.currencyconverter.data.db.CurrencyExchangeRateDao
import eunix56.example.com.currencyconverter.data.db.entity.CurrencyExchangeRate
import eunix56.example.com.currencyconverter.data.network.CurrencyNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class CurrencyConverterRepositoryImpl(
    private val currencyNetworkDataSource: CurrencyNetworkDataSource,
    private val currencyRatesDao: CurrencyExchangeRateDao
     ): CurrencyConverterRepository {

    init {
        currencyNetworkDataSource.downloadedCurrencyRates.observeForever { currencyExchangeRate ->
            persistFetchedCurrencyExchangeRate(currencyExchangeRate)
        }
    }

    private fun persistFetchedCurrencyExchangeRate(fetchedCurrencyExchangeRate: CurrencyExchangeRate) {
        GlobalScope.launch(Dispatchers.IO) {
            currencyRatesDao.upsert(fetchedCurrencyExchangeRate)
        }
    }

    private suspend fun initCurrencyRateData() {
        val lastCurrencyExchangeRate = currencyRatesDao.getCurrencyExchangeRate().value

        if (lastCurrencyExchangeRate == null) {
            fetchCurrencyRates()
            return
        }

        if (isFetchCurrencyRateNeeded(lastCurrencyExchangeRate.zonedDateTime)){
            fetchCurrencyRates()
        }
    }

    override suspend fun getCurrencyExchangeRate(): LiveData<CurrencyExchangeRate> {
        initCurrencyRateData()
        return withContext(Dispatchers.IO) {
            return@withContext currencyRatesDao.getCurrencyExchangeRate()
        }
    }

    private suspend fun fetchCurrencyRates() {
        currencyNetworkDataSource.fetchCurrencyRates()
    }

    private fun isFetchCurrencyRateNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val oneDayAgo = ZonedDateTime.now().minusDays(1)
        return lastFetchTime.isBefore(oneDayAgo)
    }

}
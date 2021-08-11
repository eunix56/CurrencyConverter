package eunix56.example.com.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import eunix56.example.com.currencyconverter.data.db.CurrencyExchangeRateDao
import eunix56.example.com.currencyconverter.data.db.LastNumOfDaysExchangeRateDao
import eunix56.example.com.currencyconverter.data.db.entity.CurrencyExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.LastNumOfDaysExchangeRate
import eunix56.example.com.currencyconverter.data.network.CurrencyNetworkDataSource
import eunix56.example.com.currencyconverter.internal.dataLiveData
import eunix56.example.com.currencyconverter.internal.utils.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class CurrencyConverterRepositoryImpl(
    private val currencyNetworkDataSource: CurrencyNetworkDataSource,
    private val currencyRatesDao: CurrencyExchangeRateDao,
    private val historyRatesDao: LastNumOfDaysExchangeRateDao
     ): CurrencyConverterRepository {

    override suspend fun getCurrencyExchangeRate(): LiveData<DataResult<CurrencyExchangeRate>> {
        return dataLiveData(
            databaseQuery = { currencyRatesDao.getCurrencyExchangeRate() },
            networkCall = { currencyNetworkDataSource.fetchCurrencyRates() },
            saveCallResult = { currencyRatesDao.upsert(it) }
        )
    }

    override suspend fun getHistoryExchangeRate(startAt: String, endAt: String): LiveData<DataResult<LastNumOfDaysExchangeRate>> {
        return dataLiveData(
            databaseQuery = { historyRatesDao.getLastNumOfDaysExchangeRate(startAt, endAt) },
            networkCall = { currencyNetworkDataSource.fetchHistoryExchangeRate(startAt, endAt) },
            saveCallResult = { historyRatesDao.upsert(it) }
        )
    }

}
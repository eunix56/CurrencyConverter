package eunix56.example.com.currencyconverter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import eunix56.example.com.currencyconverter.data.db.entity.LastNumOfDaysExchangeRate
import eunix56.example.com.currencyconverter.data.repository.CurrencyConverterRepository
import eunix56.example.com.currencyconverter.internal.lazyDeferred
import eunix56.example.com.currencyconverter.internal.utils.DataResult

class CurrencyViewModel
    (private val currencyConverterRepository: CurrencyConverterRepository)
    : ViewModel(){

    val currencyRates by lazyDeferred {
        currencyConverterRepository.getCurrencyExchangeRate()
    }

    suspend fun getHistoryRates(startDate: String, endDate: String): LiveData<DataResult<LastNumOfDaysExchangeRate>> {
        return currencyConverterRepository.getHistoryExchangeRate(startDate, endDate)
    }

}
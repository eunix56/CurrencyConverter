package eunix56.example.com.currencyconverter.ui

import androidx.lifecycle.ViewModel
import eunix56.example.com.currencyconverter.data.repository.CurrencyConverterRepository
import eunix56.example.com.currencyconverter.internal.lazyDeferred

class CurrencyViewModel
    (private val currencyConverterRepository: CurrencyConverterRepository)
    : ViewModel(){

    val currencyRates by lazyDeferred {
        currencyConverterRepository.getCurrencyExchangeRate()
    }

}
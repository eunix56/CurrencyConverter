package eunix56.example.com.currencyconverter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eunix56.example.com.currencyconverter.data.repository.CurrencyConverterRepository

class CurrencyViewModelFactory (
    private val currencyConverterRepository: CurrencyConverterRepository)
    : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrencyViewModel(currencyConverterRepository) as T
    }
}
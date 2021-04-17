package eunix56.example.com.currencyconverter.data.provider

import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem

interface CurrencyItemProvider {

    fun fetchCurrencyItems(): List<CurrencyItem>
}
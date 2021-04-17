package eunix56.example.com.currencyconverter.data.provider

import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem
import eunix56.example.com.currencyconverter.internal.utils.StringUtils

class CurrencyItemProviderImpl : CurrencyItemProvider {

    override fun fetchCurrencyItems(): List<CurrencyItem> {
        val currencyItems = mutableListOf<CurrencyItem>()

        currencyItems.add(StringUtils().currencyItemUrl("AUD"))
        currencyItems.add(StringUtils().currencyItemUrl("BGN"))
        currencyItems.add(StringUtils().currencyItemUrl("BRL"))
        currencyItems.add(StringUtils().currencyItemUrl("CAD"))
        currencyItems.add(StringUtils().currencyItemUrl("CHF"))
        currencyItems.add(StringUtils().currencyItemUrl("CNY"))
        currencyItems.add(StringUtils().currencyItemUrl("CZK"))
        currencyItems.add(StringUtils().currencyItemUrl("DKK"))
        currencyItems.add(StringUtils().currencyItemUrl("GBP"))
        currencyItems.add(StringUtils().currencyItemUrl("HKD"))
        currencyItems.add(StringUtils().currencyItemUrl("HRK"))
        currencyItems.add(StringUtils().currencyItemUrl("HUF"))
        currencyItems.add(StringUtils().currencyItemUrl("IDR"))
        currencyItems.add(StringUtils().currencyItemUrl("ILS"))
        currencyItems.add(StringUtils().currencyItemUrl("INR"))
        currencyItems.add(StringUtils().currencyItemUrl("ISK"))
        currencyItems.add(StringUtils().currencyItemUrl("JPY"))
        currencyItems.add(StringUtils().currencyItemUrl("KRW"))
        currencyItems.add(StringUtils().currencyItemUrl("MXN"))
        currencyItems.add(StringUtils().currencyItemUrl("MYR"))
        currencyItems.add(StringUtils().currencyItemUrl("NOK"))
        currencyItems.add(StringUtils().currencyItemUrl("NZD"))
        currencyItems.add(StringUtils().currencyItemUrl("NGN"))
        currencyItems.add(StringUtils().currencyItemUrl("PHP"))
        currencyItems.add(StringUtils().currencyItemUrl("PLN"))
        currencyItems.add(StringUtils().currencyItemUrl("RON"))
        currencyItems.add(StringUtils().currencyItemUrl("RUB"))
        currencyItems.add(StringUtils().currencyItemUrl("SEK"))
        currencyItems.add(StringUtils().currencyItemUrl("SGD"))
        currencyItems.add(StringUtils().currencyItemUrl("THB"))
        currencyItems.add(StringUtils().currencyItemUrl("TRY"))
        currencyItems.add(StringUtils().currencyItemUrl("USD"))
        currencyItems.add(StringUtils().currencyItemUrl("ZAR"))

        return currencyItems
    }

}
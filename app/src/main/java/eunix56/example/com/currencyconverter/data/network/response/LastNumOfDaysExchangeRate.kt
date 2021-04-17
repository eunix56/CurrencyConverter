package eunix56.example.com.currencyconverter.data.network.response

import com.google.gson.annotations.SerializedName
import eunix56.example.com.currencyconverter.data.db.entity.Rates

data class LastNumOfDaysExchangeRate(
    val base: String,
    @SerializedName("end_at")
    val endAt: String,
    @SerializedName("rates")
    val lastNumOfDaysRates: Map<String, Rates>,
    @SerializedName("start_at")
    val startAt: String
)
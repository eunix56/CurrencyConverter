package eunix56.example.com.currencyconverter.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import eunix56.example.com.currencyconverter.data.db.entity.CurrencyExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.LastNumOfDaysExchangeRate
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL="https://api.exchangerate.host/"


interface ExchangeRateApiService {
    @GET("latest")
    fun getLatestExchangeRateAsync():Deferred<CurrencyExchangeRate>

    @GET("timeseries")
    fun getLastNumOfDaysExchangeRateAsync(
        @Query("start_date") startAt: String,
        @Query("end_date") endAt: String
    ): Deferred<LastNumOfDaysExchangeRate>

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ExchangeRateApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ExchangeRateApiService::class.java)
        }
    }

}
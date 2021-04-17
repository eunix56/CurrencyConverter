package eunix56.example.com.currencyconverter.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eunix56.example.com.currencyconverter.data.db.entity.CURRENT_CURRENCY_RATES
import eunix56.example.com.currencyconverter.data.db.entity.CurrencyExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.Rates

@Dao
interface CurrencyExchangeRateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(currencyExchangeRate: CurrencyExchangeRate)

    @Query("select * from currency_rate where id = $CURRENT_CURRENCY_RATES")
    fun getCurrencyExchangeRate(): LiveData<CurrencyExchangeRate>
}
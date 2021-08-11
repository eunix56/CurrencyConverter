package eunix56.example.com.currencyconverter.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eunix56.example.com.currencyconverter.data.db.entity.CURRENT_CURRENCY_RATES
import eunix56.example.com.currencyconverter.data.db.entity.CURRENT_HISTORY_RATE
import eunix56.example.com.currencyconverter.data.db.entity.LastNumOfDaysExchangeRate

@Dao
interface LastNumOfDaysExchangeRateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(lastDaysExchangeRate: LastNumOfDaysExchangeRate)

    @Query("select * from history_exchange_rate where id = $CURRENT_HISTORY_RATE and startAt = :startAtDate and endAt = :endAtDate")
    fun getLastNumOfDaysExchangeRate(startAtDate: String, endAtDate: String): LiveData<LastNumOfDaysExchangeRate>
}
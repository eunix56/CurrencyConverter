package eunix56.example.com.currencyconverter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import eunix56.example.com.currencyconverter.data.db.entity.CurrencyExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.LastNumOfDaysExchangeRate

@Database(
    entities = [CurrencyExchangeRate::class, LastNumOfDaysExchangeRate::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(MapStringDoubleTypeConverter::class, MapStringStringDoubleTypeConverter::class)

abstract class CurrencyDatabase: RoomDatabase() {
    abstract fun currencyRatesDao(): CurrencyExchangeRateDao
    abstract fun historyRatesDao(): LastNumOfDaysExchangeRateDao

    companion object {
        @Volatile private var instance: CurrencyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                CurrencyDatabase::class.java, "currency.db")
                .build()
    }
}
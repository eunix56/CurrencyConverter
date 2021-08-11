package eunix56.example.com.currencyconverter.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

const val CURRENT_CURRENCY_RATES = 0


@Entity(tableName="currency_rate")
data class CurrencyExchangeRate(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
) {
    @PrimaryKey(autoGenerate = false)
    var id:Int = CURRENT_CURRENCY_RATES

    val zonedDateTime: ZonedDateTime
        get() {
            val zoneId = ZoneId.systemDefault()
            return LocalDateTime.parse(date).atZone(zoneId)
        }
}
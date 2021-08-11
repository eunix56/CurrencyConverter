package eunix56.example.com.currencyconverter.internal.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.format.ResolverStyle
import java.util.*

class DateUtils {

    companion object {
        fun convertToDate(date: String): ZonedDateTime {
            val zoneId = ZoneId.systemDefault()
            val formatter = DateTimeFormatterBuilder()
                .parseStrict()
                .appendPattern("uuuu-MM-dd")
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT)
            return LocalDate.parse(date, formatter).atStartOfDay(zoneId)
        }

        fun convertDayOfYearToDate(date: Float): ZonedDateTime {
            val zoneId = ZoneId.systemDefault()
            return LocalDate.ofEpochDay(date.toLong()).atStartOfDay(zoneId)
        }

        fun convertToString(date: ZonedDateTime, format: String): String {
            val formatter = DateTimeFormatter.ofPattern(format)
            return date.format(formatter)
        }

        fun getLastThirtyDaysDate(): ZonedDateTime {
            return ZonedDateTime.now().minusDays(30)
        }

        fun getLastNinetyDaysDate(): ZonedDateTime {
            return ZonedDateTime.now().minusDays(90)
        }

        fun getNowDateString(): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return ZonedDateTime.now().format(formatter)
        }
    }
}
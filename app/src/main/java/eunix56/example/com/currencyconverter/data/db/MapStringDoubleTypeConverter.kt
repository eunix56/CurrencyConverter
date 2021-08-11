package eunix56.example.com.currencyconverter.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapStringDoubleTypeConverter {

    @TypeConverter
    fun fromString(value: String): Map<String, Double> {
        val mapType = object : TypeToken<Map<String, Double>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun toString(value: Map<String, Double>): String {
        return Gson().toJson(value)
    }
}
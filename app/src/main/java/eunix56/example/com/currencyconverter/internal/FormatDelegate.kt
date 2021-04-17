package eunix56.example.com.currencyconverter.internal

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FormatDelegate : ReadOnlyProperty<Any?, String> {

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): String {
        return property.name
    }
}
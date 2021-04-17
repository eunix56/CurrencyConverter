package eunix56.example.com.currencyconverter.internal.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import eunix56.example.com.currencyconverter.R
import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem


class StringUtils {
    fun changeTextColorAfterDecimal(currency: String){
        if(!TextUtils.isEmpty(currency) && currency.contains(".")
            && isLengthAfterDecimalGreaterThanThree(currency))
            changeTextColour(currency, R.color.colorTextCurrency,
                getStartPointOfColouredText(currency), currency.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun getStartPointOfColouredText(currency: String): Int{
        return currency.indexOf(".") + 3
    }
    
    private fun isLengthAfterDecimalGreaterThanThree(text: String): Boolean {
        val stringList = text.split(".")
        return stringList[1].length > 3
    }

    private fun changeTextColour(text: String, color:Int,
                                 startPoint:Int, endPoint:Int, spannableType:Int) {
        val spannableString = SpannableString(text)
        spannableString.setSpan(
            ForegroundColorSpan(color),
            startPoint, endPoint, spannableType)
    }

    fun currencyItemUrl(text: String): CurrencyItem {
        return CurrencyItem(text, getImageSourceUrl(text))
    }

    fun getImageSourceUrl(text: String): String {
        val string = text.substring(0, 2).toLowerCase()
        return "https://www.countryflags.io/$string/flat/32.png"
    }
}
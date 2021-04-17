package eunix56.example.com.currencyconverter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.squareup.picasso.Picasso
import eunix56.example.com.currencyconverter.R
import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem

class CurrencySpinnerArrayAdapter
    (context: Context, private val currencyItems: List<CurrencyItem>,
     @LayoutRes val resourceId: Int
): ArrayAdapter<CurrencyItem>(context, resourceId) {

    val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getItemView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return currencyItems.size
    }

    override fun getItem(position: Int): CurrencyItem {
        return currencyItems[position]
    }

    private fun getItemView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val currencyItem = currencyItems[position]
        val itemView: View = convertView ?: inflater.inflate(resourceId, parent, false)
        val tvCurrencyName: TextView = itemView.findViewById(R.id.tv_currency_name) as TextView
        val ivCurrencyImage: ImageView = itemView.findViewById(R.id.iv_currency_flag) as ImageView

        tvCurrencyName.text = currencyItem.currencyName
        Picasso.with(context)
            .load(currencyItem.currencyFlagImage)
            .into(ivCurrencyImage)

        return itemView

    }
}
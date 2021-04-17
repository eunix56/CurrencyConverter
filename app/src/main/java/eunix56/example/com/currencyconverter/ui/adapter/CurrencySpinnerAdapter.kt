package eunix56.example.com.currencyconverter.ui.adapter

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import eunix56.example.com.currencyconverter.R
import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem

class CurrencySpinnerAdapter
    (private val context: Context, private val currencyItems: List<CurrencyItem>,
    @LayoutRes val resourceId: Int):
    RecyclerView.Adapter<CurrencySpinnerAdapter.RecyclerItemHolder>(), ListAdapter {
    override fun getViewTypeCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun areAllItemsEnabled(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEnabled(position: Int): Boolean {
        return false
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {

    }

    val inflater = LayoutInflater.from(context)

    override fun isEmpty(): Boolean {
        return false
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getItemView(position, convertView, parent)
    }

    override fun getItem(position: Int): Any {
        return currencyItems[position]
    }

    override fun getCount(): Int {
        return currencyItems.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerItemHolder {
        val currencyItemView = inflater.inflate(R.layout.spinner_item, p0, false)
        return RecyclerItemHolder(currencyItemView)
    }

    override fun getItemCount(): Int {
        return currencyItems.size
    }

    override fun onBindViewHolder(p0: RecyclerItemHolder, p1: Int) {
        val currencyItem = currencyItems[p1]

        p0.tvCurrencyName.text = currencyItem.currencyName

        Picasso.with(context)
            .load(currencyItem.currencyFlagImage)
            .into(p0.ivCurrencyImage)

    }


    private fun getItemView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View
        val viewHolder: ItemHolder

        itemView = inflater.inflate(resourceId, parent, false)
        viewHolder = ItemHolder(itemView)


        val currencyItem = currencyItems[position]

        viewHolder.tvCurrencyName.text = currencyItem.currencyName

        Picasso.with(context)
            .load(currencyItem.currencyFlagImage)
            .into(viewHolder.ivCurrencyImage)

        return itemView

    }

    class RecyclerItemHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {

        val tvCurrencyName: TextView = itemView.findViewById(R.id.tv_currency_name) as TextView
        val ivCurrencyImage: ImageView = itemView.findViewById(R.id.iv_currency_flag) as ImageView

    }

    private class ItemHolder(itemView: View?) {

        val tvCurrencyName: TextView
        val ivCurrencyImage: ImageView

        init {
            tvCurrencyName = itemView?.findViewById(R.id.tv_currency_name) as TextView
            ivCurrencyImage = itemView.findViewById(R.id.iv_currency_flag) as ImageView
        }

    }

}
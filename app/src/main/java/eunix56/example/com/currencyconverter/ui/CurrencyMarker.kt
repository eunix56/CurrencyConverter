package eunix56.example.com.currencyconverter.ui

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import eunix56.example.com.currencyconverter.R
import kotlinx.android.synthetic.main.custom_graph_marker.view.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class CurrencyMarker (private val contextVal: Context,
                      private val firstCurrency: String, private val secondCurrency: String)
    :MarkerView(contextVal, R.layout.custom_graph_marker) {

    override fun refreshContent(entry: Entry, highlight: Highlight) {
        super.refreshContent(entry, highlight)
        tv_selected_day.text = formatDate(entry.x.toLong())
        tv_rates_on_graph.text = formatCurrency(entry.y.toDouble())
    }

    private fun formatCurrency(entry: Double): String {
        return contextVal.getString(R.string.marker_value, firstCurrency, entry, secondCurrency)
    }

    private fun formatDate(entry: Long): String {
        val zonedDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(entry), ZoneId.systemDefault())
        return zonedDateTime.format(DateTimeFormatter.ofPattern("dd MMM, yyyy"))
    }
}

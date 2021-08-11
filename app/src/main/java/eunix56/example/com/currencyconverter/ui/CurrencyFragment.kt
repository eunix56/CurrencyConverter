package eunix56.example.com.currencyconverter.ui


import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.data.Entry
import eunix56.example.com.currencyconverter.R
import eunix56.example.com.currencyconverter.data.db.entity.CurrencyExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.LastNumOfDaysExchangeRate
import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem
import eunix56.example.com.currencyconverter.internal.utils.DataResult
import eunix56.example.com.currencyconverter.internal.utils.DateUtils
import eunix56.example.com.currencyconverter.internal.utils.StringUtils
import eunix56.example.com.currencyconverter.ui.adapter.CurrencySpinnerArrayAdapter
import eunix56.example.com.currencyconverter.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.graph_layout.*
import kotlinx.android.synthetic.main.screen_top_layout.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.ZonedDateTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 *
 */

const val PARCEL_CURRENCY_ITEMS = "currency_item_list"
const val THIRTY_DAYS = 0
const val NINETY_DAYS = 1

class CurrencyFragment : ScopedFragment(), KodeinAware {
    override val kodein by  closestKodein()
    private val viewModelFactory: CurrencyViewModelFactory by instance()

    private lateinit var viewModel: CurrencyViewModel
    private var currencyItemsList: ArrayList<CurrencyItem> = ArrayList()
    private var selectedSecondCurrencyString: String = ""
    private var selectedSecondDoubleValue: Double = 0.0
    private var selectedFirstCurrencyString: String = ""
    private var selectedFirstDoubleValue: Double = 0.0
    private var currencyMap: Map<String, Any>? = null
    private var selectedIndex = 0
    private var dateList: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currency, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CurrencyViewModel::class.java)

        bindUI(this.requireContext())
    }


    private fun bindUI(context: Context) = launch {
        val currencyRates = viewModel.currencyRates.await()

        et_second_currency.isEnabled = false

        currencyRates.observe(viewLifecycleOwner, Observer { currencyRate ->
            if (currencyRate.status == DataResult.Status.SUCCESS) {
                if (currencyRate.data == null)
                    return@Observer

                setViewForSuccess(currencyRate.data, context)
            } else if (currencyRate.status == DataResult.Status.ERROR) {
//                displayMessage(currencyRate.message)
            }

        })

        val historyRateValue = viewModel.getHistoryRates(getSelectedDate(selectedIndex), DateUtils.getNowDateString())

        historyRateValue.observe(viewLifecycleOwner, Observer { historyRate ->
            if (historyRate.status == DataResult.Status.SUCCESS) {
                if (historyRate.data == null)
                    return@Observer

                setupChart(historyRate.data)
            }
        })

        setupGraphIndex()
    }

    private fun getSelectedDate(selectedIndex: Int): String {
        return if (selectedIndex == THIRTY_DAYS) {
            DateUtils.convertToString(DateUtils.getLastThirtyDaysDate(), "yyyy-MM-dd")
        } else {
            DateUtils.convertToString(DateUtils.getLastNinetyDaysDate(), "yyyy-MM-dd")
        }
    }

    private fun setViewForSuccess(currencyRate: CurrencyExchangeRate, context: Context) {
        currencyMap = currencyRate.rates

        if (!currencyMap.isNullOrEmpty()) {
            currencyMap?.keys?.forEach {
                currencyItemsList.add(StringUtils().currencyItemUrl(it))
            }

            selectedFirstCurrencyString = currencyRate.base
            selectedSecondCurrencyString = currencyItemsList[0].currencyName
            setupFirstCurrency(selectedFirstCurrencyString)
            setupSecondCurrency(selectedSecondCurrencyString)
            setUpSpinner(context, currencyItemsList, selectedFirstCurrencyString)

            val args = Bundle()
            args.putParcelableArrayList(PARCEL_CURRENCY_ITEMS, currencyItemsList)
            openSettingsFragment(args)
        }
    }

    private fun hideProgressShowFirstTexts() {
        pb_first_currency.visibility = View.GONE
        pb_first_currency_val.visibility = View.GONE
        tv_first_currency.visibility = View.VISIBLE
        sp_first_currency.visibility = View.VISIBLE
    }

    private fun hideProgressShowSecondTexts() {
        pb_second_currency.visibility = View.GONE
        pb_second_currency_val.visibility = View.GONE
        tv_second_currency.visibility = View.VISIBLE
        sp_second_currency.visibility = View.VISIBLE
    }

    private fun openSettingsFragment(args: Bundle) {
        iv_setings_icon.setOnClickListener {
            findNavController().navigate(R.id.settings, args)
        }
    }

    private fun setupEditTextForRates(rateValue: Double?) {
        if (et_first_currency != null && !TextUtils.isEmpty(et_first_currency.text)) {
            val firstCurrencyValue = et_first_currency.text.toString().toDouble()

            val selectedCurrencyRate = firstCurrencyValue * 1/selectedFirstDoubleValue * rateValue.toString().toDouble()
            StringUtils().
                changeTextColorAfterDecimal(selectedCurrencyRate.toString())
            et_second_currency.setText(selectedCurrencyRate.toString())
        }
    }

    private fun setupButton() {
        btn_convert_currency.setOnClickListener {
            setupEditTextForRates(selectedSecondDoubleValue)
        }
    }

    private fun setupGraphIndex() {
        tv_30_days.setOnClickListener{
            selectedIndex = 0
            iv_select_past_30.visibility = View.VISIBLE
            iv_select_past_90.visibility = View.GONE
        }

        tv_90_days.setOnClickListener {
            selectedIndex = 1
            iv_select_past_30.visibility = View.GONE
            iv_select_past_90.visibility = View.VISIBLE
        }

    }

    private fun setupFirstCurrency(selectedCurrency: String?) {
        hideProgressShowFirstTexts()
        tv_first_currency.text = selectedCurrency
        if (currencyMap != null && currencyMap?.containsKey(selectedCurrency)!!) {
            selectedFirstDoubleValue = currencyMap?.getValue(selectedCurrency.toString()).toString().toDouble()
            setupButton()
        }
    }

    private fun setupSecondCurrency(selectedCurrency: String?) {
        hideProgressShowSecondTexts()
        tv_second_currency.text = selectedCurrency
        setupRates(selectedCurrency)
    }

    private fun setupRates(selectedCurrency: String?) {
        if (currencyMap != null && currencyMap?.containsKey(selectedCurrency)!!) {
            selectedSecondDoubleValue = currencyMap?.getValue(selectedCurrency.toString()).toString().toDouble()
            setupButton()
        }
    }

    private fun setupSelectionBase(baseCurrency: String?): Int {
        if (currencyItemsList.isEmpty()) return 0
        currencyItemsList.forEach {
            if (it.currencyName == baseCurrency)
                return currencyItemsList.indexOf(it)
        }
        return 0
    }

    private fun setUpSpinner(context: Context, currencyItems: List<CurrencyItem>, baseCurrency: String?) {
        val currencyAdapter = CurrencySpinnerArrayAdapter(context, currencyItems, R.layout.spinner_item)

        with(sp_second_currency) {
            adapter = currencyAdapter
            setSelection(0, false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedSecondCurrencyString = currencyItemsList[position].currencyName
                    setupSecondCurrency(selectedSecondCurrencyString)
                }

            }
            gravity = Gravity.CENTER
        }

        with(sp_first_currency) {
            adapter = currencyAdapter
            setSelection(setupSelectionBase(baseCurrency), false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedFirstCurrencyString = currencyItemsList[position].currencyName
                    setupFirstCurrency(selectedFirstCurrencyString)
                }

            }
            gravity = Gravity.CENTER
        }
    }

    private fun displayMessage() {

    }

    private fun setupChart(historyEntry: LastNumOfDaysExchangeRate) {
        val graphDrawable = context?.resources?.getDrawable(R.drawable.bg_graph)
        val graphHelper = context?.let { GraphHelper(it, currency_graph, selectedFirstCurrencyString, selectedSecondCurrencyString) }
        graphHelper?.init()

        graphHelper?.setLineChartEntries(setupGraph(historyEntry), "currency_graph", graphDrawable!!, dateList)
    }

    private fun setupGraph(historyEntry: LastNumOfDaysExchangeRate): ArrayList<Entry> {
        var firstCurrencyVal: Double
        var secondCurrencyVal: Double
        var entryVal: Double
        var date: ZonedDateTime
        var count = 0
        val listOfEntry: ArrayList<Entry> = ArrayList()
        historyEntry.lastNumOfDaysRates.forEach {
            count++

            date = DateUtils.convertToDate(it.key)

            firstCurrencyVal = it.value.getValue(selectedFirstCurrencyString)

            secondCurrencyVal = it.value.getValue(selectedSecondCurrencyString)

            entryVal = 1/ firstCurrencyVal.times(secondCurrencyVal)

            if (count % 6 == 0)
                listOfEntry.add (Entry(date.toLocalDate().toEpochDay().toFloat(), entryVal.toFloat()))
        }

        return listOfEntry
    }

}

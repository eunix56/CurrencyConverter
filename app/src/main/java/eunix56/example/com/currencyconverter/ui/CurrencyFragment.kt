package eunix56.example.com.currencyconverter.ui


import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import eunix56.example.com.currencyconverter.R
import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem
import eunix56.example.com.currencyconverter.data.db.entity.model.RatesValue
import eunix56.example.com.currencyconverter.internal.utils.StringUtils
import eunix56.example.com.currencyconverter.ui.adapter.CurrencySpinnerArrayAdapter
import eunix56.example.com.currencyconverter.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.screen_top_layout.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import androidx.core.widget.doOnTextChanged as doOnTextChanged1

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 *
 */

const val PARCEL_CURRENCY_ITEMS = "currency_item_list"

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
    private var firstCurrencyString: String = ""
    private lateinit var ratesValue: RatesValue

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
            ratesValue = RatesValue(currencyRate.rates)
            currencyMap = ratesValue.getData()
            firstCurrencyString = currencyRate.base

            if (!currencyMap.isNullOrEmpty()) {
                currencyMap?.keys?.forEach {
                    currencyItemsList.add(StringUtils().currencyItemUrl(it))
                }
                setupFirstCurrency(firstCurrencyString)
                setupSecondCurrency(currencyItemsList[0].currencyName)
                setUpSpinner(context, currencyItemsList, firstCurrencyString)

                val args = Bundle()
                args.putParcelableArrayList(PARCEL_CURRENCY_ITEMS, currencyItemsList)
                openSettingsFragment(args)
            }
        })
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

}

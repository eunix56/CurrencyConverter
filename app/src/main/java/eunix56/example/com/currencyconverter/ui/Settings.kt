package eunix56.example.com.currencyconverter.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import eunix56.example.com.currencyconverter.R
import eunix56.example.com.currencyconverter.data.db.entity.model.CurrencyItem
import eunix56.example.com.currencyconverter.ui.adapter.CurrencySpinnerAdapter
import eunix56.example.com.currencyconverter.ui.adapter.CurrencySpinnerArrayAdapter
import eunix56.example.com.currencyconverter.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.base_settings.*
import kotlinx.android.synthetic.main.settings.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

const val BASE_DIALOG_TITLE = "Select base currency"
const val LAYOUT_ITEM = 5

/**
 * A simple [Fragment] subclass.
 *
 */
class Settings : Fragment(){

    private var currencyItemList: ArrayList<CurrencyItem>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        currencyItemList = arguments?.getParcelableArrayList(PARCEL_CURRENCY_ITEMS)
        bindUI(this.requireContext())
    }

    private fun bindUI(context: Context) {

        cl_clear_cache.setOnClickListener { cl_clear_cache ->

        }

        tv_rate_app.setOnClickListener { tv_rate_app ->

        }

        tv_share_app.setOnClickListener { tv_share_app ->

        }

        tv_about_app.setOnClickListener { tv_about_app ->

        }

    }

    fun fromListtoString(stringList: List<String>?): String {
        var string = ""
        if (stringList == null || stringList.isEmpty())
            return ""
        stringList.forEach {
            string += "\n$it"
        }

        return  string
    }
}

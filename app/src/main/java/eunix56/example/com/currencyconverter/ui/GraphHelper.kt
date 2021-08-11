package eunix56.example.com.currencyconverter.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import eunix56.example.com.currencyconverter.R
import eunix56.example.com.currencyconverter.internal.utils.DateUtils
import kotlin.math.abs

class GraphHelper(val chartContext: Context, val currencyChart: LineChart,
                  val firstCurrencyVal: String, val secondCurrencyVal: String) {
    private lateinit var xAxis: XAxis
    private lateinit var yAxis: YAxis
    private lateinit var chartEntries: List<Entry>
    private lateinit var chartDataSet: LineDataSet
    private lateinit var currencyILineDataSet: ArrayList<ILineDataSet>
    private lateinit var currencyLineData: LineData
    private lateinit var xAxisLabels: List<String>


    fun init() {
        with(currencyChart) {
            fitScreen();
            setPadding(8, 0, 0, 16);
            description.isEnabled = false
            setTouchEnabled(false)
            setDrawGridBackground(false)
            isDragXEnabled = false
            setScaleEnabled(false)
            marker = CurrencyMarker(chartContext, firstCurrencyVal, secondCurrencyVal)
            isAutoScaleMinMaxEnabled = false
            setPinchZoom(false)
            setNoDataText("")
            animateXY(300, 300, Easing.Linear, Easing.Linear)
//            setExtraOffsets("8".toFloat(), "0".toFloat(), "0".toFloat(), "16".toFloat())
        }
    }

    fun setLineChartEntries(
        values: ArrayList<Entry>,
        tag: String,
        fillDrawable: Drawable,
        xAxisLabels: List<String>
    )
    {
        if (values == null)
            throw IllegalArgumentException ("Line Chart Entries cannot be NULL")

        if (xAxisLabels == null)
            throw IllegalArgumentException ("xAxis Labels cannot be NULL")

        this.xAxisLabels = xAxisLabels

        setupAxis(currencyChart)

        chartEntries = values

        chartDataSet = makeDataSet(values, tag, fillDrawable);

        currencyILineDataSet = ArrayList();

        currencyILineDataSet.add(chartDataSet);

        setLineChartData(currencyILineDataSet, currencyChart);


    }

    private fun setLineChartData(dataSets: ArrayList<ILineDataSet>, currencyChart: LineChart) {

        if (dataSets.isEmpty()) {
            return;
        }

        currencyLineData = LineData(dataSets);

        currencyChart.data = currencyLineData;

        currencyChart.setVisibleXRangeMaximum(4F);

        // get the legend (only possible after setting data)
        val legend = currencyChart.legend;
        legend.form = Legend.LegendForm.NONE; // Don't show any legend.
        legend.isEnabled = false;

        refresh(currencyChart)
    }

    private fun setupAxis(currencyChart: LineChart) {
        setupXAxis(currencyChart);
        setupYAxis(currencyChart);
    }


    private fun setupXAxis(currencyChart: LineChart) {
        xAxis = currencyChart.xAxis;

        currencyChart.setXAxisRenderer(
            CustomXAxisRenderer(currencyChart.viewPortHandler,
                xAxis, currencyChart.getTransformer(YAxis.AxisDependency.LEFT)
            ));

        xAxis.position = XAxis.XAxisPosition.BOTTOM;

        xAxis.gridColor = ContextCompat.getColor(chartContext, android.R.color.transparent);
        xAxis.axisLineColor = ContextCompat.getColor(chartContext, android.R.color.transparent);

        xAxis.gridLineWidth = 1.0f;

        xAxis.textSize = 11f;

        xAxis.granularity = 1f;

        xAxis.isGranularityEnabled = true;

        xAxis.textColor = ContextCompat.getColor(chartContext, R.color.color_whitish_green);

        xAxis.spaceMin = 0.1f;
        xAxis.spaceMax = 0.15f;

//        xAxis.setTypeface();

        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return DateUtils.convertToString(DateUtils.convertDayOfYearToDate(value), "MMM. dd")
            }
        }
    }


    private fun setupYAxis(currencyChart: LineChart) {
        yAxis = currencyChart.axisLeft;

        // disable dual axis (only use LEFT axis)
        currencyChart.axisRight.isEnabled = false
        currencyChart.axisLeft.isEnabled = false

        // axis range
        yAxis.axisMinimum = 0f;

        yAxis.gridColor = ContextCompat.getColor(chartContext, android.R.color.transparent);
        yAxis.axisLineColor = ContextCompat.getColor(chartContext, android.R.color.transparent);

        yAxis.gridLineWidth = 1.0f;

    }

    private fun applyAxisBuffer(axis: AxisBase, axisMax: Float, axisMin:  Float) {
        var bufferBase = abs(axisMax);
        if (axisMax.equals(0.toFloat())) {
            bufferBase = abs(axisMin);
        }

        val buffer = 0.5.toFloat()
        axis.axisMaximum = axisMax + buffer;
        axis.axisMinimum = axisMin - buffer;
    }

    private fun applyAxisMax(axisBase: AxisBase) {
        axisBase.resetAxisMinimum();
        axisBase.resetAxisMaximum();
    }

    private fun refresh(currencyChart: LineChart)
    {
        currencyChart.data.notifyDataChanged();
        currencyChart.notifyDataSetChanged();
        currencyChart.invalidate();
    }

    private fun makeDataSet(
        values: ArrayList<Entry>,
        tag: String,
        fillDrawable: Drawable
    ):  LineDataSet{
        val set: LineDataSet;

        val chartData = currencyChart.data;
        if (chartData != null && chartData.dataSetCount > 0) {
            set = chartData.getDataSetByLabel (tag, true) as LineDataSet
            set.values = values;
            set.notifyDataSetChanged();
            chartData.notifyDataChanged();
            currencyChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set = LineDataSet (values, tag)

            set.setDrawIcons(false);

            // line thickness and point size
            set.lineWidth = 2f;

            // draw points as solid circles

            set.setDrawCircleHole(false);
            set.setDrawCircles(false);
//            set.circleRadius = 4f;
//            set.circleHoleRadius = 2.5f
//            set.setCircleColor(chartContext.resources.getColor(R.color.color_whitish_green));
//            set.circleHoleColor = chartContext.resources.getColor(R.color.colorGreen)

            // customize legend entry
            // set.setFormLineWidth(1f);
            // set.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            // set.setFormSize(15.f);

            // text size of values
            // set.setValueTextSize(9f);
            set.setDrawValues(false);

            // draw selection line as dashed
            set.disableDashedLine();
            set.setDrawHorizontalHighlightIndicator(false);

            set.fillFormatter =
                IFillFormatter { _, _ -> currencyChart.axisLeft.axisMinimum }
        }

        set.color = chartContext.resources.getColor(R.color.color_whitish_green);

        set.setDrawFilled(true);
        // set color of filled area
        if (Utils.getSDKInt() >= 18) {
            // drawables only supported on api level 18 and above
            set.fillDrawable = fillDrawable;
        } else {
            set.fillColor = Color.TRANSPARENT;
        }

        return set;
    }

//    private fun selectTrendsChartBarAtIndex(int index, LineChart trendChart) {
//        if (index >= 0) {
//           Handler ().post(() -> {
//                trendChart.highlightValue(index, 0, true);
//                trendChart.moveViewToX(index - 2);
//            });
//        }

//    }
}
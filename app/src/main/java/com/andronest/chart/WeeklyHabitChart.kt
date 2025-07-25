package com.andronest.chart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.Calendar

@Composable
fun WeeklyHabitChart(completions: List<Long>) {

    val chartData = processChartData(completions)

    AndroidView(
        factory = { context->
            BarChart(context).apply {
                description.isEnabled = false
                setDrawGridBackground(false)
                setTouchEnabled(true)
                isDragEnabled = true
                setScaleEnabled(true)

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                    setDrawGridLines(false)
                }

                axisLeft.apply {
                    granularity = 1f
                    axisMinimum = 0f
                }

                axisRight.isEnabled = false
                legend.isEnabled = false
                animateY(1000)
            }
        },
        update = { chart->

            val entries = chartData.mapIndexed { index,  count ->
                BarEntry(index.toFloat(), count.toFloat())
            }

            val dataSet = BarDataSet(entries,"Daily Completions").apply {

                color = Color.Blue.toArgb()
                valueTextColor = Color.Black.toArgb()
                valueTextSize = 10f
            }

            chart.data = BarData(dataSet)
            chart.xAxis.valueFormatter = DayAxisFormatter()
            chart.invalidate()
        },
        modifier = Modifier.fillMaxSize()
    )
}
// Custom X-axis labels (days of week)
class DayAxisFormatter : ValueFormatter() {
    private val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    override fun getFormattedValue(value: Float): String {
        return days.getOrNull(value.toInt()) ?: ""
    }
}


private fun processChartData(completions: List<Long>): List<Int>{

    val dailyCounts = MutableList(7) { 0 }

    completions.forEach { timestamp->

        val calendar = Calendar.getInstance().apply { timeInMillis = timestamp }
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        // Calendar.DAY_OF_WEEK
        // Sunday(day 1):  (1 + 5) % 7 = 6 % 7 = 6
        // Monday(day 2):  (2 + 5) % 7 = 7 % 7 = 0
        // Saturday(day 7):  (7 + 5) % 7 = 12 % 7 = 5
        if(dayOfWeek in 1..7){
            val index = (dayOfWeek + 5) % 7
            dailyCounts[index] = dailyCounts[index]+1
        }
    }

    return dailyCounts
}
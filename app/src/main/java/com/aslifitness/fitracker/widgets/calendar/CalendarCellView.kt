package com.aslifitness.fitracker.widgets.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.ItemCalendarCellBinding
import com.aslifitness.fitracker.routine.data.Label
import com.aslifitness.fitracker.widgets.calendar.data.CellInfo

/**
 * Created by shubhampandey
 */
class CalendarCellView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): LinearLayout(context, attributeSet, defyStyle) {

    private val binding = ItemCalendarCellBinding.inflate(LayoutInflater.from(context), this, true)
    private val dimen2Dp = context.resources.getDimension(R.dimen.dimen_2dp).toInt()
    private var cellInfo: CellInfo? = null

    fun setData(cellInfo: CellInfo) {
        this.cellInfo = cellInfo
        binding.date.text = cellInfo.date.toString()
        configureLabels(cellInfo.labels)
    }

    private fun configureLabels(labels: List<Label>?) {
        if (labels.isNullOrEmpty()) return
        for (label in labels) {
            val drawable = ContextCompat.getDrawable(context, R.drawable.rect_shape_button)
            label.color?.let {
                drawable?.mutate()?.setColorFilter(Color.parseColor(it), PorterDuff.Mode.MULTIPLY)
            }
            val item = AppCompatTextView(context).apply {
                gravity = Gravity.CENTER
                background = drawable
                includeFontPadding = false
                setPadding(dimen2Dp, dimen2Dp, dimen2Dp, dimen2Dp)
                text = label.name
                layoutParams = getLayoutParamsWithHeight()
            }.also {
                TextViewCompat.setTextAppearance(it, R.style.ExtraSmall_Regular_White)
            }
            binding.labelContainer.addView(item)
        }
    }

    private fun getLayoutParamsWithHeight(): LayoutParams {
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        layoutParams.topMargin = context.resources.getDimension(R.dimen.dimen_2dp).toInt()
        return layoutParams
    }

    fun setLabels(labels: List<Label>?) {
        configureLabels(labels)
    }

    fun getCurrentDate() = cellInfo?.date

    fun setTextColor(color: Int) {
        binding.date.setTextColor(color)
    }

    fun setSelected() {
        binding.date.background = ContextCompat.getDrawable(context, R.drawable.ic_dot_green)
    }
}
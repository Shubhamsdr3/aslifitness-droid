package com.aslifitness.fitrackers.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitrackers.databinding.WorkoutQtySelectorBinding
import com.aslifitness.fitrackers.model.SetCountInfo
import com.aslifitness.fitrackers.utils.setTextWithVisibility

/**
 * @author Shubham Pandey
 */
class WorkoutQtySelector @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = WorkoutQtySelectorBinding.inflate(LayoutInflater.from(context), this, true)
    private var callback: WorkoutQtySelectorCallback? = null

    fun setData(qtyInfo: SetCountInfo, callback: WorkoutQtySelectorCallback) {
        this.callback = callback
        binding.numText.setTextWithVisibility(qtyInfo.setCount.toString())
        configureListener()
    }

    private fun configureListener() {
        binding.startBtn.setOnClickListener { onPlusClicked() }
        binding.endBtn.setOnClickListener { onMinusClicked() }
    }

    private fun onMinusClicked() {
        var currentQty = getCurrentQty()
        binding.numText.setTextWithVisibility("${--currentQty}")
        callback?.onDecrementClicked()
    }

    private fun onPlusClicked() {
        var currentQty = getCurrentQty()
        binding.numText.setTextWithVisibility("${++currentQty}")
        callback?.onIncrementClicked()
    }

    private fun getCurrentQty(): Int {
        return binding.numText.text?.toString()?.toInt() ?: 0
    }
}
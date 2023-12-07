package com.aslifitness.fitrackers.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitrackers.databinding.LayoutNoWorkoutBinding
import com.aslifitness.fitrackers.model.NoDataInfo
import com.aslifitness.fitrackers.utils.setTextWithVisibility

/**
 * Created by shubhampandey
 */
class NoDataView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = LayoutNoWorkoutBinding.inflate(LayoutInflater.from(context), this, true)
    fun setData(noDataInfo: NoDataInfo, callback: () -> Unit) {
        noDataInfo.run {
            binding.title.setTextWithVisibility(title)
            binding.btnWorkoutSummary.setButtonTitle(ctaText)
        }
        binding.btnWorkoutSummary.setOnClickListener {
            Toast.makeText(context, "On button clicked..", Toast.LENGTH_SHORT).show()
            callback.invoke()
        }
    }
}
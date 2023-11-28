package com.aslifitness.fitrackers.summary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.LayoutSummaryCardViewBinding
import com.aslifitness.fitrackers.summary.data.WorkoutSummary
import com.aslifitness.fitrackers.utils.setCircularImage
import com.aslifitness.fitrackers.utils.setTextWithVisibility
import kotlinx.parcelize.RawValue

/**
 * Created by shubhampandey
 */
class SummaryCardView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = LayoutSummaryCardViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(data: WorkoutSummary) {
        data.run {
            binding.image.setCircularImage(image, R.drawable.ic_dumble_new)
            binding.title.setTextWithVisibility(title)
            configureText(binding.duration.valueText, durationInfo?.value)
            configureText(binding.volume.valueText, volumeInfo?.value)
            configureText(binding.setCount.valueText, setInfo?.value)
        }
    }

    private fun configureText(valueText: AppCompatTextView, value: @RawValue Any?) {
        value?.let { valueText.setTextWithVisibility(value.toString()) }
    }
}
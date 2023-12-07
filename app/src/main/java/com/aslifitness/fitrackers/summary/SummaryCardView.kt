package com.aslifitness.fitrackers.summary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.LayoutSummaryCardViewBinding
import com.aslifitness.fitrackers.model.NoDataInfo
import com.aslifitness.fitrackers.summary.data.WorkoutSummary
import com.aslifitness.fitrackers.utils.setCircularImage
import com.aslifitness.fitrackers.utils.setTextWithVisibility
import kotlinx.parcelize.RawValue

/**
 * Created by shubhampandey
 */
class SummaryCardView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = LayoutSummaryCardViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var callback: SummaryCardViewListener? = null
    fun setData(data: WorkoutSummary?) {
        if (data == null) {
            binding.showData = false
            setNoWorkoutData()
            binding.executePendingBindings()
        } else {
            configureView(data)
        }
    }

    private fun setNoWorkoutData() {
        val noDataInfo = NoDataInfo(
            title = context.getString(R.string.no_workout_text),
            ctaText = context.getString(R.string.add_exercise)
        )
        binding.noWorkout.setData(noDataInfo) {
            callback?.onAddWorkoutClicked()
        }

        binding.noWorkout.setOnClickListener {
            Toast.makeText(context, "On no data clicked..", Toast.LENGTH_SHORT).show()
        }
    }

    fun setSummaryViewListener(callback: SummaryCardViewListener) {
        this.callback = callback
    }

    private fun configureView(data: WorkoutSummary) {
        binding.showData = true
        binding.executePendingBindings()
        data.run {
            binding.image.setCircularImage(image, R.drawable.ic_dumble_new)
            binding.title.setTextWithVisibility(title)
            configureText(binding.duration.keyText, durationInfo?.key)
            configureText(binding.duration.valueText, durationInfo?.value)
            configureText(binding.volume.keyText, durationInfo?.key)
            configureText(binding.volume.valueText, volumeInfo?.value)
            configureText(binding.setCount.keyText, setInfo?.key)
            configureText(binding.setCount.valueText, setInfo?.value)
        }
    }

    private fun configureText(valueText: AppCompatTextView, value: @RawValue Any?) {
        value?.let { valueText.setTextWithVisibility(value.toString()) }
    }
}
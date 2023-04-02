package com.aslifitness.fitracker.widgets.addworkout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.ItemAddSetViewBinding
import com.aslifitness.fitracker.model.addworkout.WorkoutSetInfo
import com.aslifitness.fitracker.utils.setTextWithVisibility

/**
 * Created by shubhampandey
 */
class AddSetWidget @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = ItemAddSetViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var setWeightInKg: Int = 0
    private var setRepsCount: Int = 0
    private var callback: AddSetCallback? = null
    private var setInfo: WorkoutSetInfo? = null

    fun setData(setInfo: WorkoutSetInfo, index: Int, callback: AddSetCallback) {
        this.callback = callback
        this.setInfo = setInfo
        setInfo.run {
            binding.index.setTextWithVisibility((index + 1).toString())
            binding.weightInKg.hint = weightInKg.toString()
            binding.repsCount.hint = repsCount.toString()
            if (index % 2 == 0) binding.root.background = ContextCompat.getDrawable(context, R.drawable.rect_light_grey_bg)
        }
        configureListener()
    }

    private fun configureListener() {
        binding.done.setOnClickListener { onDoneClicked() }
        binding.weightInKg.addTextChangedListener { text ->
            if (!text.isNullOrEmpty()) {
                setWeightInKg = text.toString().toInt()
            }
        }
        binding.repsCount.addTextChangedListener { text ->
            if (!text.isNullOrEmpty()) {
                setRepsCount = text.toString().toInt()
            }
        }
    }

    private fun onDoneClicked() {
        if (setWeightInKg > 0 && setRepsCount > 0) {
            binding.done.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_check_active))
            binding.root.background = ContextCompat.getDrawable(context, R.drawable.rect_light_green_bg)
            setInfo?.run {
                this.isDone = true
                this.weightInKg = setWeightInKg
                this.repsCount = setRepsCount
                callback?.onDoneClicked(this)
            }
        } else {
            setInfo?.run {
                isDone = false
                if (weightInKg > 0) {
                    message = "Please add reps count"
                    callback?.onDoneClicked(this)
                } else if (repsCount > 0) {
                    message = "Please add weight"
                    callback?.onDoneClicked(this)
                } else {
                    message =  "Please add set count"
                    callback?.onDoneClicked(this)
                }
            }
        }
    }
}
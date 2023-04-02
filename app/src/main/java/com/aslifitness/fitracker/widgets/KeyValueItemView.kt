package com.aslifitness.fitracker.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitracker.databinding.ItemKeyValueViewBinding
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.model.SetCountInfo

/**
 * @author Shubham Pandey
 */
class KeyValueItemView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = ItemKeyValueViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(workout: Workout) {
        binding.key.text = workout.header
        setQuantity(workout.qtyInfo)
    }

    fun setQuantity(qtyInfo: SetCountInfo?) {
        qtyInfo?.let { binding.value.text = qtyInfo.setCount.toString() }
    }
}
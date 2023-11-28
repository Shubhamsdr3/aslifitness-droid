package com.aslifitness.fitrackers.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.WidgetAddWorkoutBinding
import com.aslifitness.fitrackers.detail.data.Workout
import com.aslifitness.fitrackers.model.SetCountInfo
import com.aslifitness.fitrackers.model.WorkoutSetData
import com.aslifitness.fitrackers.utils.setTextWithVisibility

/**
 * @author Shubham Pandey
 */
class AddWorkoutWidget @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = WidgetAddWorkoutBinding.inflate(LayoutInflater.from(context), this, true)
    private var callback: AddWorkoutWidgetCallback? = null
    private val dimen0dp = context.resources.getDimension(R.dimen.dimen_0dp).toInt()
    private var lastIndex: Int = 0
    private var isExtended: Boolean? = null
    private var workoutSetData: WorkoutSetData? = null

    fun setData(data: WorkoutSetData, callback: AddWorkoutWidgetCallback) {
        this.callback = callback
        this.isExtended = data.isExtended
        this.workoutSetData = data
        binding.title.setTextWithVisibility(data.date)
        configureSets(data.sets, data.isExtended)
        configureListener()
    }

    private fun configureSets(sets: List<Workout>?, isExtended: Boolean?) {
        if (sets.isNullOrEmpty()) return
        lastIndex = sets.count()
        if (isExtended == null || isExtended == true) {
            setupSetList(sets)
        }
    }

    private fun setupSetList(sets: List<Workout>) {
        for (idx in sets.indices) {
            val widget = KeyValueItemView(context)
            val setItem = sets[idx]
            widget.setData(sets[idx])
            binding.container.addView(widget, getLayoutParamsWithMargins())
            widget.setOnClickListener { callback?.onItemClicked(idx, setItem) }
        }
    }

    private fun getLayoutParamsWithMargins(): LinearLayout.LayoutParams {
        val margin16Dp = context.resources.getDimension(R.dimen.dimen_16dp).toInt()
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(dimen0dp, margin16Dp, dimen0dp, dimen0dp)
        return params
    }

    private fun configureListener() {
        binding.icPlus.setOnClickListener { configureSetList() }
    }

    private fun configureSetList() {
        when(isExtended) {
            true -> setupCollapseView()
            false -> setupExtendedView()
            else -> callback?.onPlusClicked(binding.container.childCount)
        }
    }

    private fun setupExtendedView() {
        workoutSetData?.run {
            if (!sets.isNullOrEmpty()) { setupSetList(sets) }
        }
        this.isExtended = true
    }

    private fun setupCollapseView() {
        binding.container.removeAllViews()
        this.isExtended = false
    }

    fun setQuantity(position: Int, qtyInfo: SetCountInfo) {
        val widgetAt = binding.container.getChildAt(position)
        if (widgetAt != null && widgetAt is KeyValueItemView) {
            widgetAt.setQuantity(qtyInfo)
        }
    }

    fun onAddNewSetClicked(position: Int, workout: Workout) {
        KeyValueItemView(context).apply {
            setData(workout)
            binding.container.addView(this, getLayoutParamsWithMargins())
            setOnClickListener { callback?.onItemClicked(position, workout) }
        }
    }
}
package com.aslifitness.fitracker.widgets.addworkout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitracker.databinding.LayoutAddWorkoutWidgetBinding
import com.aslifitness.fitracker.model.CtaInfo
import com.aslifitness.fitracker.model.addworkout.NewAddWorkout
import com.aslifitness.fitracker.model.addworkout.WorkoutSetInfo
import com.aslifitness.fitracker.utils.setTextWithVisibility

class AddNewWorkoutWidget @JvmOverloads constructor(
    context: Context, attributesSet:
    AttributeSet? = null,
    defyStyle: Int = 0
): ConstraintLayout(context, attributesSet, defyStyle), AddSetCallback, SetCountSelectorCallback {

    private val binding = LayoutAddWorkoutWidgetBinding.inflate(LayoutInflater.from(context), this, true)
    private var setCount = 0
    private var callback: AddWorkoutWidgetCallback? = null
    private var newAddWorkout: NewAddWorkout? = null

    fun setData(data: NewAddWorkout, callback: AddWorkoutWidgetCallback) {
        this.callback = callback
        this.newAddWorkout = data
        binding.workoutTitle.setTextWithVisibility(data.title)
        configureSets(data.sets)
        configureCta(data.addSetCta)
    }

    private fun configureCta(cta: CtaInfo?) {
        cta?.run { binding.addButton.setData(this, this@AddNewWorkoutWidget) }
    }

    private fun onAddButtonClicked() {
        if (newAddWorkout != null && !newAddWorkout?.sets.isNullOrEmpty()) {
            val defaultSet = newAddWorkout?.sets?.get(0)
            defaultSet?.workoutId = newAddWorkout!!.workoutId
            val itemView = AddSetWidget(context)
            itemView.setData(defaultSet!!, setCount++, this)
            binding.setContainer.addView(itemView)
        }
    }

    private fun configureSets(sets: List<WorkoutSetInfo>?) {
        if (sets.isNullOrEmpty()) return
        setCount = sets.count()
        for (index in sets.indices) {
            val data = sets[index]
            val itemView = AddSetWidget(context)
            itemView.setData(data, index, this)
            binding.setContainer.addView(itemView)
        }
    }

    override fun onDoneClicked(setInfo: WorkoutSetInfo) {
        callback?.onSetCompleted(setInfo)
    }

    override fun onPlusClicked() {
        onAddButtonClicked()
    }

    override fun onMinusClicked() {
        onRemoveClicked()
    }

    private fun onRemoveClicked() {
        val itemCount = binding.setContainer.childCount
        if (itemCount > 0) {
            binding.setContainer.removeViewAt(itemCount - 1)
        }
    }
}
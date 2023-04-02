package com.aslifitness.fitracker.widgets.addworkout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitracker.databinding.LayoutAddWorkoutBinding
import com.aslifitness.fitracker.model.CtaInfo
import com.aslifitness.fitracker.model.addworkout.NewAddWorkout
import com.aslifitness.fitracker.model.addworkout.WorkoutSetInfo
import com.aslifitness.fitracker.utils.setImageWithVisibility
import com.aslifitness.fitracker.utils.setTextWithVisibility

class AddWorkoutWidget @JvmOverloads constructor(context: Context, attributesSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributesSet, defyStyle) {

    private val binding = LayoutAddWorkoutBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(data: NewAddWorkout) {
        binding.workoutTitle.setTextWithVisibility(data.header)
        binding.imageWorkout.setImageWithVisibility(data.image)
        configureSets(data.sets)
        configureCtas(data.ctas)
    }

    private fun configureCtas(ctas: List<CtaInfo>?) {

    }

    private fun configureSets(sets: List<WorkoutSetInfo>?) {

    }
}
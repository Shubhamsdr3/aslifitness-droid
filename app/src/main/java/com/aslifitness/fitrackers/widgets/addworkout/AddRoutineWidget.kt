package com.aslifitness.fitrackers.widgets.addworkout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitrackers.databinding.WidgetAddRoutineBinding

class AddRoutineWidget @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = WidgetAddRoutineBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData() {

    }
}
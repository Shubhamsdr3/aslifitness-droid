package com.aslifitness.fitracker.routine.summary

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.databinding.ItemRoutineViewHolderBinding
import com.aslifitness.fitracker.routine.data.UserRoutineDto

/**
 * Created by shubhampandey
 */
class ItemRoutineViewHolder(private val itemBinding: ItemRoutineViewHolderBinding): RecyclerView.ViewHolder(itemBinding.root) {

    fun bindData(data: UserRoutineDto, callback: () -> Unit) {
        data.run { itemBinding.routineWidget.setData(this, callback) }
    }
}
package com.aslifitness.fitrackers.routine.summary

import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.databinding.ItemRoutineViewHolderBinding
import com.aslifitness.fitrackers.routine.data.UserRoutineDto

/**
 * Created by shubhampandey
 */
class ItemRoutineViewHolder(private val itemBinding: ItemRoutineViewHolderBinding): RecyclerView.ViewHolder(itemBinding.root) {

    fun bindData(data: UserRoutineDto, callback: () -> Unit) {
        data.run { itemBinding.routineWidget.setData(this, callback) }
    }
}
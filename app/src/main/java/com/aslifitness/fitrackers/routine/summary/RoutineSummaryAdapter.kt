package com.aslifitness.fitrackers.routine.summary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.databinding.ItemRoutineViewHolderBinding
import com.aslifitness.fitrackers.routine.data.UserRoutineDto

/**
 * Created by shubhampandey
 */
class RoutineSummaryAdapter(private val routines: List<UserRoutineDto>, private val callback: RoutineAdapterCallback): RecyclerView.Adapter<ItemRoutineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRoutineViewHolder {
        val itemBinding = ItemRoutineViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        itemBinding.startBtn.setOnClickListener { callback.onStartRoutineClicked() }
        return ItemRoutineViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemRoutineViewHolder, position: Int) {
        holder.bindData(routines[position]) { callback.onOptionMenuClicked() }
    }

    override fun getItemCount() = routines.count()
}
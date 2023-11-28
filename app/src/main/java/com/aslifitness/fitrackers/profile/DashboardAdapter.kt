package com.aslifitness.fitrackers.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.databinding.ItemDashboardViewBinding
import com.aslifitness.fitrackers.model.WorkoutDto

/**
 * @author Shubham Pandey
 */
class DashboardAdapter(private val cardItemList: List<WorkoutDto>, private val callback: DashboardAdapterCallback): RecyclerView.Adapter<DashboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val itemView = ItemDashboardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DashboardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.bindData(cardItemList[position])
        holder.itemView.setOnClickListener { onItemClicked(position) }
    }

    private fun onItemClicked(position: Int) {
        if (position < cardItemList.count()) {
            callback.onItemClicked(cardItemList[position])
        }
    }

    override fun getItemCount() = cardItemList.count()
}
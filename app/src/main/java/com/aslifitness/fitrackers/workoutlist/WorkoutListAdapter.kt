package com.aslifitness.fitrackers.workoutlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.databinding.ItemWorkoutListBinding
import com.aslifitness.fitrackers.detail.data.Workout
import com.aslifitness.fitrackers.utils.EMPTY

/**
 * @author Shubham Pandey
 */
class WorkoutListAdapter(private val workoutList: List<Workout>, private val callback: WorkoutListAdapterCallback): RecyclerView.Adapter<WorkoutItemViewHolder>(), Filterable {

    private var filteredItems: MutableList<Workout> = workoutList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutItemViewHolder {
        val binding = ItemWorkoutListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutItemViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val item = filteredItems[position]
            holder.bindData(item)
            holder.itemView.setOnClickListener { callback.onItemClicked(position, item) }
        }
    }

    override fun getItemCount() = filteredItems.count()

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: EMPTY
                filteredItems = if (charString.isEmpty()) {
                    workoutList.toMutableList()
                } else {
                    val filteredList = mutableListOf<Workout>()
                    workoutList
                        .filter { (!it.header.isNullOrEmpty() && (it.header.contains(constraint!!, true) || it.header.startsWith(constraint, true))) }
                        .forEach { filteredList.add(it) }
                    filteredList
                }
                return FilterResults().apply { values = filteredItems }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values == null) {
                    filteredItems.addAll(emptyList())
                } else {
                    filteredItems = results.values as MutableList<Workout>
                }
                notifyDataSetChanged()
            }
        }
    }
}
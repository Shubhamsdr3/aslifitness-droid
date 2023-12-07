package com.aslifitness.fitrackers.home

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.LayoutWorkoutBttomsheetBinding
import com.aslifitness.fitrackers.model.WorkoutDto
import com.aslifitness.fitrackers.utils.ZERO
import com.aslifitness.fitrackers.workoutlist.WorkoutListActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.ArrayList

/**
 * Created by shubhampandey
 */
class WorkoutsBottomSheet: BottomSheetDialogFragment(), WorkOutAdapterCallback {

    private lateinit var binding: LayoutWorkoutBttomsheetBinding

    companion object {

        const val TAG = "WorkoutsBottomSheet"

        private const val WORKOUT_LIST = "workout_list"
        private const val SPAN_COUNT = 2
        private const val PADDING = 36
        fun newInstance(workoutList: List<WorkoutDto>) = WorkoutsBottomSheet().apply {
            arguments = bundleOf(Pair(WORKOUT_LIST, workoutList))
        }
    }

    override fun getTheme() = R.style.BottomSheetDialogStyle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LayoutWorkoutBttomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val workoutList = arguments?.getParcelableArrayList<WorkoutDto>(WORKOUT_LIST)
        configureView(workoutList)
    }

    private fun configureView(workoutList: ArrayList<WorkoutDto>?) {
        if (!workoutList.isNullOrEmpty()) {
            val gridLayoutManager = GridLayoutManager(requireContext(), SPAN_COUNT, RecyclerView.VERTICAL, false)
            binding.workoutList.layoutManager = gridLayoutManager
            val workoutAdapter = WorkoutListAdapter(workoutList, this)
            binding.workoutList.adapter = workoutAdapter
            binding.workoutList.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val childIndex = parent.indexOfChild(view)
                    if (childIndex in workoutList.count() - SPAN_COUNT..workoutList.count()) {
                        outRect.bottom = ZERO
                    } else {
                        outRect.bottom = PADDING
                    }
                    if (childIndex  % 2 == 0) {
                        outRect.right = PADDING
                    }
                }
            })
        }
    }

    override fun onWorkoutSelected(workoutDto: WorkoutDto) {
        workoutDto.title?.let { WorkoutListActivity.start(requireContext(), it) }
    }
}
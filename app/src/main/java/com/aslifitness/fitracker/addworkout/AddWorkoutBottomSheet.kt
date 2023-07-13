package com.aslifitness.fitracker.addworkout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.FragmentAddWorkoutBottomBinding
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.model.addworkout.WorkoutBottomSheetInfo
import com.aslifitness.fitracker.utils.setCircularImage
import com.aslifitness.fitracker.utils.setImageWithPlaceholder
import com.aslifitness.fitracker.utils.setTextWithVisibility
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * @author Shubham Pandey
 */
class AddWorkoutBottomSheet:  BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddWorkoutBottomBinding
    private var callback: WorkoutBottomSheetCallback? = null

    companion object {
        const val TAG = "AddWorkoutBottomSheet"
        private const val WORKOUT_INFO = "workout_info"

        fun newInstance(workoutInfo: WorkoutBottomSheetInfo) = AddWorkoutBottomSheet().apply {
            arguments = bundleOf(Pair(WORKOUT_INFO, workoutInfo))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment != null && parentFragment is WorkoutBottomSheetCallback) {
            this.callback = parentFragment as WorkoutBottomSheetCallback
        } else if (context is WorkoutBottomSheetCallback) {
            this.callback = context
        }
    }

    override fun getTheme() = R.style.BottomSheetDialogStyle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddWorkoutBottomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val workoutInfo = arguments?.getParcelable<WorkoutBottomSheetInfo>(WORKOUT_INFO)
        initView(workoutInfo)
        setupListener()
    }

    private fun setupListener() {
        binding.workoutContainer.setOnClickListener { callback?.onAddWorkoutClicked() }
        binding.routineContainer.setOnClickListener { callback?.onAddRoutineClicked() }
        binding.historyButton.setOnClickListener { callback?.openHistoryClicked() }
    }

    private fun initView(workoutInfo: WorkoutBottomSheetInfo?) {
        workoutInfo?.run {
            binding.title.setTextWithVisibility(header)
            binding.workoutImage.setImageWithPlaceholder(workout?.image, R.drawable.ic_dumble_new)
            binding.workoutTitle.setTextWithVisibility(workout?.title)
            binding.routineImage.setImageWithPlaceholder(routine?.image, R.drawable.ic_calendar_today_24)
            binding.routineTitle.setTextWithVisibility(routine?.title)
        }
    }
}
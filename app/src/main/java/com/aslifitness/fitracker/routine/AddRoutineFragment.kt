package com.aslifitness.fitracker.routine

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.FragmentAddRoutineBinding
import com.aslifitness.fitracker.db.AppDatabase
import com.aslifitness.fitracker.model.addworkout.NewAddWorkout
import com.aslifitness.fitracker.network.ApiHandler
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.notification.NotificationDto
import com.aslifitness.fitracker.notification.NotificationUtil
import com.aslifitness.fitracker.routine.data.RoutineWorkout
import com.aslifitness.fitracker.routine.data.UserRoutineDto
import com.aslifitness.fitracker.routine.summary.RoutineSummaryActivity
import com.aslifitness.fitracker.sharedprefs.UserStore
import com.aslifitness.fitracker.utils.EMPTY
import com.aslifitness.fitracker.utils.ROUTINE_SUMMARY_DEEPLINK
import com.aslifitness.fitracker.widgets.AddRoutineWidget
import com.aslifitness.fitracker.workoutlist.WorkoutFragmentCallback

/**
 * Created by shubhampandey
 */
class AddRoutineFragment : Fragment(), WorkoutFragmentCallback {

    private lateinit var binding: FragmentAddRoutineBinding
    private var callback: AddRoutineFragmentCallback? = null
    private lateinit var viewModel: AddRoutineViewModel
    private val routineWorkouts = mutableListOf<RoutineWorkout>()
    private val notificationUtil by lazy { NotificationUtil(requireContext()) }

    companion object {
        const val TAG = "AddRoutineFragment"
        fun newInstance() = AddRoutineFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment != null && parentFragment is AddRoutineFragmentCallback) {
            this.callback = parentFragment as AddRoutineFragmentCallback
        } else if (context is AddRoutineFragmentCallback) {
            this.callback = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    private fun initViewModel() {
        val factory = AddRoutineViewModelFactory(RoutineRepository(ApiHandler.apiService, AppDatabase.getInstance()))
        viewModel = ViewModelProvider(this, factory)[AddRoutineViewModel::class.java]
        viewModel.userRoutineState.observe(viewLifecycleOwner) { onRoutineAddState(it) }
    }

    private fun onRoutineAddState(state: NetworkState<UserRoutineDto>?) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Error -> showError()
            is NetworkState.Success -> handleSuccess(state.data)
            else -> {
                // do nothing
            }
        }
    }

    private fun handleSuccess(data: UserRoutineDto?) {
        binding.saveButton.hideLoader()
        data?.run { configureReminder(workouts) }
        startActivity(Intent(activity, RoutineSummaryActivity::class.java))
    }

    private fun configureReminder(workouts: List<RoutineWorkout>?) {
        if (workouts.isNullOrEmpty()) return
        workouts.forEach {
            val notificationDto = NotificationDto(
                title = it.title,
                message = it.subTitle,
                isScheduled = it.routineInfo?.reminder?.isRepeat,
                scheduledTime = it.routineInfo?.reminder?.time,
                deeplinkUrl = ROUTINE_SUMMARY_DEEPLINK
            )
            notificationUtil.scheduleAlarm(notificationDto)
        }
    }

    private fun showError() {
        binding.saveButton.hideLoader()
    }

    private fun showLoader() {
        binding.saveButton.showLoader()
    }

    private fun initView() {
        binding.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        binding.addButton.setOnClickListener { callback?.onAddExerciseClicked() }
        binding.etTitle.setOnFocusChangeListener { v, _ ->
            if (v.hasFocus()) {
                binding.etTitle.hint = EMPTY
            } else {
                binding.etTitle.hint = getString(R.string.routine_title)
            }
        }
        binding.saveButton.setOnClickListener { onSaveRoutineClicked() }
    }

    private fun onSaveRoutineClicked() {
        val workoutTitle = binding.etTitle.text
        if (workoutTitle.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please enter title", Toast.LENGTH_SHORT).show()
            return
        }
        if (routineWorkouts.isEmpty()) {
            Toast.makeText(requireContext(), "Please add workouts", Toast.LENGTH_SHORT).show()
            return
        }
        val userRoutineDto = UserRoutineDto(
            userId = UserStore.getUserId(),
            title = workoutTitle.toString(),
            workouts = routineWorkouts,
            createdAt = System.currentTimeMillis()
        )
        viewModel.saveRoutineToLocalDb(userRoutineDto)
    }

    fun addWorkouts(workoutList: List<RoutineWorkout>) {
        routineWorkouts.clear()
        routineWorkouts.addAll(workoutList)
        routineWorkouts.forEach {
            val routineWidget = AddRoutineWidget(requireContext())
            routineWidget.setData(it)
            binding.workoutContainer.addView(routineWidget)
            hideNoWorkoutView()
        }
    }

    private fun hideNoWorkoutView() {
        binding.workoutIcon.visibility = View.GONE
        binding.description.visibility = View.GONE
    }

    override fun onWorkoutSelected(workoutList: List<NewAddWorkout>) {
        // do nothing
    }
}

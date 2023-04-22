package com.aslifitness.fitracker.routine

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.addworkout.WorkoutRepository
import com.aslifitness.fitracker.databinding.FragmentAddRoutineBinding
import com.aslifitness.fitracker.db.UserRoutine
import com.aslifitness.fitracker.model.addworkout.NewAddWorkout
import com.aslifitness.fitracker.model.addworkout.WorkoutSetInfo
import com.aslifitness.fitracker.network.ApiHandler
import com.aslifitness.fitracker.network.RetrofitHandler
import com.aslifitness.fitracker.routine.data.UserRoutineDto
import com.aslifitness.fitracker.sharedprefs.UserStore
import com.aslifitness.fitracker.utils.EMPTY
import com.aslifitness.fitracker.widgets.AddRoutineWidget
import com.aslifitness.fitracker.widgets.AddRoutineWidgetCallback
import com.aslifitness.fitracker.widgets.AddWorkoutWidget
import com.aslifitness.fitracker.widgets.addworkout.AddNewWorkoutWidget
import com.aslifitness.fitracker.widgets.addworkout.AddWorkoutWidgetCallback
import com.aslifitness.fitracker.workoutlist.WorkoutFragmentCallback
import com.aslifitness.fitracker.workoutlist.WorkoutListFragment
import com.google.firebase.firestore.auth.User

/**
 * Created by shubhampandey
 */
class AddRoutineFragment : Fragment(), WorkoutFragmentCallback {

    private lateinit var binding: FragmentAddRoutineBinding
    private var callback: AddRoutineFragmentCallback? = null
    private lateinit var viewModel: AddRoutineViewModel
    private val routineWorkouts = mutableListOf<NewAddWorkout>()
    private val userRoutine: UserRoutine by lazy { UserRoutine() }

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    private fun initViewModel() {
        val factory = AddRoutineViewModelFactory(WorkoutRepository(ApiHandler.apiService))
        viewModel = ViewModelProvider(this, factory)[AddRoutineViewModel::class.java]
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
            workouts = routineWorkouts
        )
        viewModel.saveUserRoutine(userRoutineDto)
    }

    fun addWorkouts(workoutList: List<NewAddWorkout>) {
        routineWorkouts.addAll(workoutList)
        workoutList.forEach {
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

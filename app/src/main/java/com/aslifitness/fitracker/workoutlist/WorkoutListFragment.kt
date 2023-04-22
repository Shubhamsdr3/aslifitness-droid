package com.aslifitness.fitracker.workoutlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.addworkout.AddWorkoutViewModel
import com.aslifitness.fitracker.addworkout.AddWorkoutViewModelFactory
import com.aslifitness.fitracker.create.CreateNewWorkoutActivity
import com.aslifitness.fitracker.databinding.FragmentWorkoutListBinding
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.model.WorkoutListResponse
import com.aslifitness.fitracker.model.addworkout.NewAddWorkout
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.utils.setTextWithVisibility
import com.aslifitness.fitracker.widgets.searchbar.FitnessSearchCallback

/**
 * @author Shubham Pandey
 */
class WorkoutListFragment: Fragment(), WorkoutListAdapterCallback {

    private lateinit var binding: FragmentWorkoutListBinding
    private lateinit var viewModel: WorkoutListViewModel
    private lateinit var addWorkoutViewModel: AddWorkoutViewModel
    private var callback: WorkoutFragmentCallback? = null
    private var title: String? = null
    private val addedNewWorkout = mutableListOf<Workout>()

    companion object {
        const val TAG = "WorkoutListFragment"
        private const val TITLE = "title"
        private const val FOCUS_SEARCH = "focus_search"

        fun newInstance(title: String?, focusSearch: Boolean?) = WorkoutListFragment().apply {
            arguments = bundleOf(Pair(TITLE, title), Pair(FOCUS_SEARCH, focusSearch))
        }

        fun newInstance() = WorkoutListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment != null && parentFragment is WorkoutFragmentCallback) {
            this.callback = parentFragment as WorkoutFragmentCallback
        } else if (context is WorkoutFragmentCallback) {
            this.callback = context
        } else {
            throw RuntimeException("$context must implement fragment context")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWorkoutListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractBundle()
        setupInitialView()
        setupViewModel()
    }

    private fun extractBundle() {
        arguments?.let {
            title = it.getString(TITLE)
            val focusSearch = it.getBoolean(FOCUS_SEARCH)
            if (focusSearch) binding.searchBar.requestFocus()
        }
    }

    private fun setupInitialView() {
        binding.searchBar.setLeftIcon(R.drawable.ic_search_24)
        binding.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        binding.createButton.setOnClickListener { CreateNewWorkoutActivity.start(requireActivity()) }
        setupSearchBar()
        setupSubmitButton()
    }

    private fun setupSubmitButton() {
        binding.submitButton.setButtonText(requireContext().getString(R.string.exercise_count, 0))
        binding.submitButton.setOnClickListener {
            if (addedNewWorkout.isNotEmpty()) {
                val newWorkoutList = addedNewWorkout.map { getNewWorkout(it) }
                addWorkoutViewModel.addNewWorkout(newWorkoutList)
                callback?.onWorkoutSelected(newWorkoutList)
            }
        }
    }

    private fun setupSearchBar() {
        binding.searchBar.setLeftIcon(R.drawable.ic_search_24)
        binding.searchBar.addOnTextChange(object : FitnessSearchCallback {
            override fun onTextChanged(text: String) {
                (binding.searchWorkouts.adapter as? WorkoutListAdapter)?.filter?.filter(text)
            }
        })
        binding.searchBar.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                v.background = ContextCompat.getDrawable(requireContext(), R.drawable.rect_primary_border)
            } else {
                v.background = ContextCompat.getDrawable(requireContext(), R.drawable.rect_grey_border)
            }
        }
    }

    private fun getNewWorkout(workout: Workout): NewAddWorkout {
        return NewAddWorkout(
            workoutId = workout.workoutId,
            image = workout.image,
            title = workout.header,
            subTitle = workout.subHeader,
            sets = workout.prevSets,
            addSetCta = workout.cta
        )
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, WorkoutListViewModelFactory())[WorkoutListViewModel::class.java]
        addWorkoutViewModel = ViewModelProvider(requireActivity(), AddWorkoutViewModelFactory())[AddWorkoutViewModel::class.java]
        viewModel.fetchWorkoutList()
        viewModel.workoutListViewState.observe(viewLifecycleOwner) { onViewStateChanged(it) }
    }

    private fun onViewStateChanged(state: NetworkState<ApiResponse<WorkoutListResponse>>?) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Error -> showError(state.throwable)
            is NetworkState.Success -> handlerResponse(state.data)
            else -> {
                // do nothing.
            }
        }
    }

    private fun handlerResponse(response: ApiResponse<WorkoutListResponse>?) {
        binding.loader.visibility = View.GONE
        response?.data?.run {
            binding.workoutHeader.setTextWithVisibility(header)
            if (!workoutList.isNullOrEmpty()) {
                val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                binding.searchWorkouts.layoutManager = layoutManager
                binding.searchWorkouts.adapter = WorkoutListAdapter(workoutList, this@WorkoutListFragment)
                binding.searchWorkouts.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
                binding.searchWorkouts.visibility = View.VISIBLE
            } else {
                binding.searchWorkouts.visibility = View.GONE
            }
        }
    }

    override fun onItemClicked(position: Int, item: Workout) {
        if (item.isSelected) {
            item.isSelected = false
            addedNewWorkout.remove(item)
        } else {
            item.isSelected = true
            addedNewWorkout.add(item)
        }
        binding.searchWorkouts.adapter?.notifyItemChanged(position)
        if (addedNewWorkout.size > 0) {
            binding.submitButton.enableButton()
        } else {
            binding.submitButton.disableButton()
        }
        binding.submitButton.setButtonText(requireContext().getString(R.string.exercise_count, addedNewWorkout.size))
    }

    private fun showError(throwable: Throwable) {
        binding.loader.visibility = View.GONE
    }

    private fun showLoader() {
        binding.loader.visibility = View.VISIBLE
    }
}

package com.aslifitness.fitracker.addworkout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.HomeActivity
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.FragmentAddWorkoutBinding
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.model.AddWorkoutDto
import com.aslifitness.fitracker.model.CtaInfo
import com.aslifitness.fitracker.model.SetCountInfo
import com.aslifitness.fitracker.model.WorkoutSetData
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.utils.Utility
import com.aslifitness.fitracker.utils.setTextWithVisibility
import com.aslifitness.fitracker.widgets.AddWorkoutWidget
import com.aslifitness.fitracker.widgets.AddWorkoutWidgetCallback
import com.aslifitness.fitracker.widgets.ItemCardViewListener
import com.aslifitness.fitracker.widgets.WorkoutHistoryCallback
import com.aslifitness.fitracker.workoutlist.WorkoutListActivity
import javax.inject.Inject

/**
 * @author Shubham Pandey
 */
class AddWorkoutFragment: Fragment(), ItemCardViewListener, CounterFragmentCallback, AddWorkoutWidgetCallback, WorkoutHistoryCallback, AddWorkoutDialogCallback {

    private var name: String? = null
    private var workout: Workout? = null
    private var workoutSetData: WorkoutSetData? = null
    private lateinit var binding: FragmentAddWorkoutBinding

    @Inject
    lateinit var viewModel: AddWorkoutViewModel

    companion object {
        const val TAG = "AddWorkoutFragment"
        private const val NAME = "name"

        fun newInstance(name: String?) = AddWorkoutFragment().apply {
            arguments = bundleOf(Pair(NAME, name))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { name = it.getString(NAME) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        binding.toolbar.title = name
        binding.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        binding.saveButton.setButtonText(requireContext().getString(R.string.done))
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(viewModelStore, AddWorkoutViewModelFactory())[AddWorkoutViewModel::class.java]
        viewModel.fetchAddWorkoutDetail()
        viewModel.fetchWorkoutSets()
        viewModel.addWorkoutState.observe(viewLifecycleOwner) { onNetworkStateChanged(it) }
        viewModel.addWorkoutViewState.observe(viewLifecycleOwner) { onViewStateChanged(it) }
        viewModel.workoutSetListLiveData.observe(viewLifecycleOwner) { onWorkoutSetFetched(it) }
        viewModel.saveWorkoutLiveData.observe(viewLifecycleOwner) { onSaveWorkoutStateChanged(it) }
    }

    private fun onSaveWorkoutStateChanged(state: NetworkState<WorkoutSetData>?) {
        when(state) {
            is NetworkState.Loading -> binding.saveButton.showLoader()
            is NetworkState.Error -> binding.saveButton.hideLoader()
            is NetworkState.Success -> onWorkoutSaved()
            else -> {
                // do nothing.
            }
        }
    }

    private fun onWorkoutSaved() {
        binding.saveButton.hideLoader()
        AddWorkoutDialog.newInstance().show(childFragmentManager, AddWorkoutDialog.TAG)
    }

    private fun onWorkoutSetFetched(state: NetworkState<List<WorkoutSetData>>?) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Error -> showError()
            is NetworkState.Success -> handleWorkoutSaved(state.data)
            else -> {
                // do nothing
            }
        }
    }

    private fun handleWorkoutSaved(data: List<WorkoutSetData>?) {
        if (!data.isNullOrEmpty()) {
            for (idx in data.indices) {
                val workoutSet = data[idx]
                workoutSet.isExtended = false
                val widget = AddWorkoutWidget(requireContext())
                widget.setData(workoutSet, this)
                binding.workoutHistory.addView(widget, getLayoutParamsWithMargins())
            }
        }
    }

    private fun getLayoutParamsWithMargins(): LinearLayout.LayoutParams {
        val margin16Dp = requireContext().resources.getDimension(R.dimen.dimen_10dp).toInt()
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, margin16Dp, 0, margin16Dp)
        return params
    }

    private fun onViewStateChanged(state: AddWorkoutState?) {
        when(state) {
            is AddWorkoutState.AddNewSet -> openCounterDialog(state.position, state.workout, true)
            else -> {
                // do nothing
            }
        }
    }

    private fun onNetworkStateChanged(state: NetworkState<ApiResponse<AddWorkoutDto>>?) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Error -> showError()
            is NetworkState.Success -> handleResponse(state.data)
            else -> {
                // do nothing
            }
        }
    }

    private fun handleResponse(response: ApiResponse<AddWorkoutDto>?) {
        binding.loader.visibility = View.GONE
        response?.data?.run {
            configureHeader()
            configureWorkouts(setData)
            configureCta(cta)
//            saveUserData(setData)
        }
    }

    private fun configureHeader() {
        binding.historyHeader.setTextWithVisibility("Past workouts: ")
    }

    private fun saveUserData(data: WorkoutSetData?) {
        data?.let { viewModel.saveInitial(it) }
    }

    private fun configureCta(cta: CtaInfo?) {
        cta?.run {
            binding.saveButton.setButtonText(text)
            binding.saveButton.setOnClickListener { onSaveButtonClicked() }
        }
    }

    private fun onSaveButtonClicked() {
        workoutSetData?.let {
            it.date = Utility.getCurrentTime()
            viewModel.saveWorkout(it)
        }
    }

    private fun configureWorkouts(workoutSetData: WorkoutSetData?) {
        this.workoutSetData = workoutSetData
        workoutSetData?.let {
            binding.setView.setData(it, this)
            binding.setView.visibility = View.VISIBLE
        }
    }

    private fun openCounterDialog(index: Int, workout: Workout?, isNewSet: Boolean) {
        workout?.let {
            childFragmentManager.beginTransaction().apply {
                add(CounterDialogFragment.newInstance(index, it, isNewSet), CounterDialogFragment.TAG)
            }.commitAllowingStateLoss()
        }
    }

    override fun onSubmitClicked(position: Int, count: Int, isNewSet: Boolean) {
        workout?.run {
            qtyInfo?.setCount = count
            if (isNewSet && count > 0) {
                binding.setView.onAddNewSetClicked(position, this)
            } else {
                qtyInfo?.let { binding.setView.setQuantity(position, it) }
            }
        }
    }

    override fun onPlusClicked(position: Int) {
        this.workout = Workout("",  "Set-${position}", "", SetCountInfo(0))
        this.workout?.let {
            this.workoutSetData?.sets?.add(it)
            viewModel.updateViewState(AddWorkoutState.AddNewSet(position, it))
        }
    }

    override fun onItemClicked(position: Int, workout: Workout) {
        this.workout = workout
        openCounterDialog(position, workout, false)
    }

    private fun showError() {
        binding.loader.visibility = View.GONE
    }

    private fun showLoader() {
        binding.loader.visibility = View.VISIBLE
    }

    override fun onAddWorkoutClicked() {
        startActivity(Intent(activity, WorkoutListActivity::class.java))
    }

    override fun onNextExerciseClicked() {
        startActivity(Intent(activity, HomeActivity::class.java))
    }

    override fun onRightIconClicked(action: String?, actionUrl: String?) {
        // do nothing
    }

    override fun onDropDownClicked() {
        // do nothing
    }

    override fun onPlusClicked() {
        // do nothing
    }
}
package com.aslifitness.fitracker.addworkout

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.HomeActivity
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.FragmentAddWorkoutBinding
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.model.addworkout.NewAddWorkout
import com.aslifitness.fitracker.model.addworkout.WorkoutSetInfo
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.summary.data.WorkoutSummaryResponse
import com.aslifitness.fitracker.utils.setCircularImage
import com.aslifitness.fitracker.utils.setTextWithVisibility
import com.aslifitness.fitracker.widgets.ItemCardViewListener
import com.aslifitness.fitracker.widgets.WorkoutHistoryCallback
import com.aslifitness.fitracker.widgets.addworkout.AddNewWorkoutWidget
import com.aslifitness.fitracker.widgets.addworkout.AddWorkoutWidgetCallback
import com.aslifitness.fitracker.workoutlist.WorkoutListActivity
import com.google.android.material.snackbar.Snackbar

/**
 * @author Shubham Pandey
 */
class AddWorkoutFragment: Fragment(), ItemCardViewListener,
    CounterFragmentCallback, AddWorkoutWidgetCallback, WorkoutHistoryCallback, AddWorkoutDialogCallback {

    private var workoutSetData: List<NewAddWorkout>? = null
    private lateinit var binding: FragmentAddWorkoutBinding
    private var callback: AddWorkoutFragmentCallback? = null
    private var viewModel: AddWorkoutViewModel? = null

    companion object {
        const val TAG = "AddWorkoutFragment"
        private const val NEW_WORKOUT = "new_workout"

        fun newInstance(newAddWorkout: List<NewAddWorkout>) = AddWorkoutFragment().apply {
            arguments = bundleOf(Pair(NEW_WORKOUT, newAddWorkout))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment != null && parentFragment is AddWorkoutFragmentCallback) {
            this.callback = parentFragment as AddWorkoutFragmentCallback
        } else if (context is AddWorkoutFragmentCallback) {
            this.callback = context
        } else {
            throw IllegalStateException("Fragment must implement $context")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractBundle()
        setupView()
        setupViewModel()
    }

    private fun extractBundle() {
        arguments?.let { workoutSetData = it.getParcelableArrayList(NEW_WORKOUT) }
    }

    private fun configureNewWorkout(workoutList: List<NewAddWorkout>?) {
        this.workoutSetData = workoutList
        if (workoutSetData.isNullOrEmpty()) {
            configureDefaultView()
            return
        }
        workoutSetData?.forEach {
            val widget = AddNewWorkoutWidget(requireContext())
            widget.setData(it, this)
            binding.setContainer.addView(widget, getLayoutParamsWithMargins())
            binding.setContainer.visibility = View.VISIBLE
            binding.addWorkout.root.visibility = View.VISIBLE
        }
    }

    private fun configureDefaultView() {
        binding.emptyWorkout.image.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_dumble_new))
        binding.emptyWorkout.title.text = getString(R.string.get_started)
        binding.emptyWorkout.subTitle.text = getString(R.string.add_workout_text)
        binding.emptyWorkout.root.visibility = View.VISIBLE
    }

    private fun configureFloatingAction() {
        binding.setContainer.post {
            if (!isViewVisible(binding.addWorkout.root)) {
                binding.floatingButton.visibility = View.VISIBLE
                binding.addWorkout.root.visibility = View.GONE
            } else {
                binding.floatingButton.visibility = View.GONE
                binding.addWorkout.root.visibility = View.VISIBLE
            }
        }
    }

    private fun isViewVisible(view: View): Boolean {
        val scrollBounds = Rect()
        binding.scrollView.getDrawingRect(scrollBounds)
        val top = view.y
        val bottom = top + view.height
        return scrollBounds.top < top && scrollBounds.bottom > bottom
    }

    private fun setupView() {
        binding.toolbar.title = getString(R.string.log_workout)
        binding.toolbar.setNavigationOnClickListener { callback?.onBackPressed() }
        binding.floatingButton.setOnClickListener { activity?.onBackPressed() }
        configureHeader()
        configureAddCta()
        configureFinishButton()
        configureScrollListener()
    }

    private fun configureHeader() {
//        binding.duration.keyText.setTextWithVisibility(getString(R.string.duration))
//        binding.duration.valueText.setTextWithVisibility("00:00:00")
        binding.volume.keyText.setTextWithVisibility(getString(R.string.volume))
        binding.volume.valueText.setTextWithVisibility("0 kg")
        binding.setCount.keyText.setTextWithVisibility(getString(R.string.sets))
        binding.setCount.valueText.setTextWithVisibility("0")
    }

    private fun configureScrollListener() {
        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, _, _, _ ->
//            configureFloatingAction()
        })
    }

    private fun configureFinishButton() {
        binding.saveButton.setOnClickListener { viewModel?.postUserWorkouts(0L) }
    }

    private fun configureAddCta() {
        binding.addWorkout.root.background = ContextCompat.getDrawable(requireContext(), R.drawable.rect_primary_bg)
        binding.addWorkout.itemTitle.setTextWithVisibility(requireContext().getString(R.string.add_exercise))
        TextViewCompat.setTextAppearance(binding.addWorkout.itemTitle, R.style.TextBody_Medium_White)
        binding.addWorkout.itemIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_add_24))
        binding.addWorkout.itemIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))
        binding.addWorkout.itemIcon.visibility = View.VISIBLE
        binding.addWorkout.root.setOnClickListener { activity?.onBackPressed() }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity(), AddWorkoutViewModelFactory())[AddWorkoutViewModel::class.java]
        viewModel?.addWorkoutState?.observe(viewLifecycleOwner) { onNetworkStateChanged(it) }
        viewModel?.addWorkoutViewState?.observe(viewLifecycleOwner) { onViewStateChanged(it) }
        viewModel?.addedWorkoutList?.observe(viewLifecycleOwner) { configureNewWorkout(it) }
    }

    private fun updateWeight(weight: Int?) {
        if (weight != null) { binding.volume.valueText.setTextWithVisibility("$weight kg") }
    }

    private fun getLayoutParamsWithMargins(): LinearLayout.LayoutParams {
        val margin16Dp = requireContext().resources.getDimension(R.dimen.dimen_16dp).toInt()
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, margin16Dp, 0, margin16Dp)
        return params
    }

    private fun onViewStateChanged(state: AddWorkoutState?) {
        when(state) {
            is AddWorkoutState.AddNewSet -> openCounterDialog(state.position, state.workout, true)
            is AddWorkoutState.UpdateSetCount -> updateSetCount(state.setCount)
            is AddWorkoutState.UpdateWeight -> updateWeight(state.weight)
            else -> {
                // do nothing
            }
        }
    }

    private fun updateSetCount(count: Int) {
        binding.setCount.valueText.setTextWithVisibility((count).toString())
    }

    private fun onNetworkStateChanged(state: NetworkState<ApiResponse<WorkoutSummaryResponse>>?) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Error -> showError(state.throwable)
            is NetworkState.Success -> handleResponse(state.data)
            else -> {
                // do nothing
            }
        }
    }

    private fun handleResponse(response: ApiResponse<WorkoutSummaryResponse>?) {
        binding.loader.visibility = View.GONE
        response?.data?.let { callback?.onWorkoutAdded(it) }
    }

    private fun openCounterDialog(index: Int, workout: Workout?, isNewSet: Boolean) {
        workout?.let {
            childFragmentManager.beginTransaction().apply {
                add(CounterDialogFragment.newInstance(index, it, isNewSet), CounterDialogFragment.TAG)
            }.commitAllowingStateLoss()
        }
    }

    override fun onSetCompleted(workoutId: Int, setInfo: WorkoutSetInfo) {
        setInfo.run {
            if (!isDone && !message.isNullOrEmpty()) {
                Snackbar.make(binding.snackBar, message!!, Snackbar.LENGTH_SHORT).show()
            } else {
                viewModel?.addSetToWorkout(workoutId, setInfo)
            }
        }
    }

    private fun showError(throwable: Throwable?) {
        binding.saveButton.hideLoader()
    }

    private fun showLoader() {
        binding.saveButton.showLoader()
    }

    override fun onAddWorkoutClicked() {
        startActivity(Intent(activity, WorkoutListActivity::class.java))
    }

    override fun onNextExerciseClicked() {
        startActivity(Intent(activity, HomeActivity::class.java))
    }

    override fun onSubmitClicked(position: Int, count: Int, isNewSet: Boolean) {
        // do nothing
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
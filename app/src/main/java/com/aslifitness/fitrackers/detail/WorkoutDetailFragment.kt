package com.aslifitness.fitrackers.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.addworkout.AddWorkoutActivity
import com.aslifitness.fitrackers.databinding.FragmentWorkoutDetailBinding
import com.aslifitness.fitrackers.detail.data.Workout
import com.aslifitness.fitrackers.detail.data.WorkoutDetailResponse
import com.aslifitness.fitrackers.model.CardItem
import com.aslifitness.fitrackers.model.CtaActionType
import com.aslifitness.fitrackers.network.ApiResponse
import com.aslifitness.fitrackers.network.NetworkState
import com.aslifitness.fitrackers.widgets.*

/**
 * @author Shubham Pandey
 */
class WorkoutDetailFragment: Fragment(), WorkoutHistoryCallback, ItemCardViewListener, WorkoutListWidgetCallback {

    private lateinit var binding: FragmentWorkoutDetailBinding

    private lateinit var viewModel: WorkoutDetailViewModel

    companion object {
        const val TAG = "WorkoutDetailFragment"
        private const val WORKOUT = "workout"

        fun newInstance(workout: String) = WorkoutDetailFragment().apply {
            arguments = bundleOf(Pair(WORKOUT, workout))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWorkoutDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractExtras()
        setupListener()
        observerChanges()
    }

    private fun setupListener() {
        binding.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    private fun extractExtras() {
        arguments?.let {
            val workoutName = it.getString(WORKOUT, "")
            binding.toolbar.title = workoutName
        }
    }

    private fun observerChanges() {
        viewModel = ViewModelProvider(this, WorkoutDetailViewModelFactory())[WorkoutDetailViewModel::class.java]
        viewModel.getLastWorkout()
        viewModel.workoutViewState.observe(viewLifecycleOwner) { onStateChanged(it) }
    }

    private fun onStateChanged(response: NetworkState<ApiResponse<WorkoutDetailResponse>>?) {
        when(response) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Error -> showError()
            is NetworkState.Success -> handleResponse(response.data)
            else -> {
                // do nothing
            }
        }
    }

    private fun handleResponse(response: ApiResponse<WorkoutDetailResponse>?) {
        binding.loader.visibility = View.GONE
        response?.data?.run {
            configureHeader(header)
            configureWorkoutList(workoutList)
        } ?: kotlin.run {
            showResponseEmptyError() // move this to viewmodel.
        }
    }

    private fun configureWorkoutList(workoutList: List<Workout>?) {
        if (!workoutList.isNullOrEmpty()) {
            val workoutListWidget = WorkoutListWidget(requireContext())
            binding.linearLayout.addView(workoutListWidget, getLayoutParamsWithTopMargin())
            workoutListWidget.setData(workoutList, this)
        }
    }

    private fun configureHeader(cardItem: CardItem?) {
        cardItem?.let {
            val cardItemView = ItemCardView(requireContext())
            cardItemView.setData(it, this)
            cardItemView.setLeftIcon(R.drawable.ic_fitness_24)
            cardItemView.setRightIcon(R.drawable.ic_chevron_right_24)
            binding.linearLayout.addView(cardItemView, getLayoutParamsWithTopMargin())
        }
    }

    private fun getLayoutParamsWithTopMargin(): LinearLayout.LayoutParams {
        val margin24Dp = requireContext().resources.getDimension(R.dimen.dimen_24dp).toInt()
        val margin16Dp = requireContext().resources.getDimension(R.dimen.dimen_16dp).toInt()
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(margin16Dp, margin24Dp, margin16Dp, margin24Dp)
        return params
    }

    private fun getLayoutParamsWithMargins(): LinearLayout.LayoutParams {
        val margin16Dp = requireContext().resources.getDimension(R.dimen.dimen_16dp).toInt()
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(margin16Dp, margin16Dp, margin16Dp, margin16Dp)
        return params
    }

    override fun onRightIconClicked(action: String?, actionUrl: String?) {
        when(action) {
            CtaActionType.REDIRECT -> AddWorkoutActivity.start(requireActivity())
        }
    }

    override fun onDropDownClicked() {
        Toast.makeText(requireContext(), "On add exercise clicked..", Toast.LENGTH_SHORT).show()
    }

    override fun onPlusClicked() {
        // do nothing..
    }

    override fun onWorkoutSelected(workout: Workout) {
        Toast.makeText(requireContext(), "On add exercise clicked..", Toast.LENGTH_SHORT).show()
    }

    private fun showError() {
        binding.loader.visibility = View.GONE
    }

    private fun showLoader() {
        binding.loader.visibility = View.VISIBLE
    }

    private fun showResponseEmptyError() {
        // do nothing..
    }
}
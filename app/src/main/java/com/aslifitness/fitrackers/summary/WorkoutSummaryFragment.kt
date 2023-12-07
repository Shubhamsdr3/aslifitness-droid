package com.aslifitness.fitrackers.summary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitrackers.addworkout.WorkoutRepository
import com.aslifitness.fitrackers.auth.UserAuthActivity
import com.aslifitness.fitrackers.databinding.FragmentWorkoutSummaryBinding
import com.aslifitness.fitrackers.network.ApiHandler
import com.aslifitness.fitrackers.network.ApiResponse
import com.aslifitness.fitrackers.network.NetworkState
import com.aslifitness.fitrackers.sharedprefs.UserStore
import com.aslifitness.fitrackers.summary.data.WorkoutSummaryResponse
import com.aslifitness.fitrackers.utils.EMPTY
import com.aslifitness.fitrackers.utils.setTextWithVisibility
import com.aslifitness.fitrackers.workoutlist.WorkoutListActivity

/**
 * Created by shubhampandey
 */
class WorkoutSummaryFragment: Fragment() {

    private lateinit var binding: FragmentWorkoutSummaryBinding
    private lateinit var viewModel: WorkoutSummaryViewModel

    companion object {
        const val TAG = "WorkoutSummaryFragment"
        private const val WORKOUT_SUMMARY = "workout_summary"
        fun newInstance(data: WorkoutSummaryResponse) = WorkoutSummaryFragment().apply {
            arguments = bundleOf(Pair(WORKOUT_SUMMARY, data))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWorkoutSummaryBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractBundle()
        initializeView()
        setUpViewModel()
    }

    private fun initializeView() {
        binding.done.setOnClickListener { activity?.finish() }
    }

    private fun extractBundle() {
        val workoutSummary = arguments?.getParcelable<WorkoutSummaryResponse>(WORKOUT_SUMMARY)
        configureWorkoutSummary(workoutSummary)
    }

    private fun setUpViewModel() {
        val factory = WorkoutSummaryVMFactory(WorkoutRepository(ApiHandler.apiService))
        viewModel = ViewModelProvider(this, factory)[WorkoutSummaryViewModel::class.java]
//        viewModel.getWorkoutSummary(workoutSummaryId)
        viewModel.workoutSummary.observe(viewLifecycleOwner) { onViewStateChanged(it) }
    }

    private fun onViewStateChanged(state: NetworkState<ApiResponse<WorkoutSummaryResponse>>?) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Error -> showError()
//            is NetworkState.Success -> handleSuccessResponse(state.data)
            else -> {
                // do nothing
            }
        }
    }

    private fun configureWorkoutSummary(data: WorkoutSummaryResponse?) {
        binding.summaryLoader.visibility = View.GONE
        data?.data?.run {
            binding.header.setTextWithVisibility(header)
            binding.date.setTextWithVisibility(subHeader)
            binding.summaryCard.setData(summary)
        }
    }

    private fun showError() {
        binding.summaryLoader.visibility = View.GONE
        binding.summaryError.root.visibility = View.VISIBLE
    }

    private fun showLoader() {
        binding.summaryLoader.visibility = View.VISIBLE
    }
}
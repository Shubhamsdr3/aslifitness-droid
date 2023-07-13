package com.aslifitness.fitracker.routine.summary

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.FragmentRoutineSummaryBinding
import com.aslifitness.fitracker.db.AppDatabase
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.model.WorkoutSetData
import com.aslifitness.fitracker.network.ApiHandler
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.routine.RoutineRepository
import com.aslifitness.fitracker.routine.data.RoutineWorkout
import com.aslifitness.fitracker.routine.data.UserRoutineDto
import com.aslifitness.fitracker.utils.setTextWithVisibility
import com.aslifitness.fitracker.widgets.AddWorkoutWidgetCallback
import com.aslifitness.fitracker.widgets.MenuOptionBottomSheet


/**
 * Created by shubhampandey
 */
class RoutineSummaryFragment: Fragment(), RoutineAdapterCallback, AddWorkoutWidgetCallback {

    private lateinit var binding: FragmentRoutineSummaryBinding
    private lateinit var viewModel: RoutineSummaryViewModel

    companion object {
        const val TAG = "RoutineSummaryFragment"

        fun newInstance() = RoutineSummaryFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRoutineSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        val factory = RoutineViewModelFactory(RoutineRepository(ApiHandler.apiService, AppDatabase.getInstance()))
        viewModel = ViewModelProvider(this, factory)[RoutineSummaryViewModel::class.java]
        viewModel.fetchUserRoutine()
        viewModel.userRoutineLiveData.observe(viewLifecycleOwner) { onRoutineFetched(it) }
    }

    private fun onRoutineFetched(state: NetworkState<List<UserRoutineDto>>?) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Success -> handleRoutineSuccess(state.data)
            is NetworkState.Error -> showError(state.throwable)
            else -> {
                // do nothing
            }
        }
    }

    private fun handleRoutineSuccess(data: List<UserRoutineDto>?) {
        if (data.isNullOrEmpty()) return
        configureAddedRoutine(data[0])
        binding.routines.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.routines.adapter = RoutineSummaryAdapter(data.subList(1, data.size), this)
        binding.routines.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.top = resources.getDimension(R.dimen.dimen_16dp).toInt()
            }
        })
        binding.routines.visibility = View.VISIBLE
    }

    private fun configureAddedRoutine(routine: UserRoutineDto) {
        binding.newRoutine.setData(routine) {
            // do nothing
        }
    }

    private fun showError(throwable: Throwable) {

    }

    private fun showLoader() {

    }

    override fun onOptionMenuClicked() {
        MenuOptionBottomSheet.newInstance(ArrayList(viewModel.getMenuOptions())).show(childFragmentManager, MenuOptionBottomSheet.TAG)
    }

    override fun onStartRoutineClicked() {

    }

    override fun onPlusClicked(position: Int) {

    }

    override fun onItemClicked(position: Int, workout: Workout) {

    }
}
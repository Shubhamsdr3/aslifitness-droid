package com.aslifitness.fitrackers.routine.summary

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.FragmentRoutineSummaryBinding
import com.aslifitness.fitrackers.db.AppDatabase
import com.aslifitness.fitrackers.detail.data.Workout
import com.aslifitness.fitrackers.network.ApiHandler
import com.aslifitness.fitrackers.network.NetworkState
import com.aslifitness.fitrackers.routine.RoutineRepository
import com.aslifitness.fitrackers.routine.data.UserRoutineDto
import com.aslifitness.fitrackers.widgets.AddWorkoutWidgetCallback
import com.aslifitness.fitrackers.widgets.MenuOptionBottomSheet


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
        binding.routineTitle.text = getString(R.string.my_routine, data.size)
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
        // do nothing
    }

    private fun showLoader() {
        // do nothing.
    }

    override fun onOptionMenuClicked() {
        MenuOptionBottomSheet.newInstance(ArrayList(viewModel.getMenuOptions())).show(childFragmentManager, MenuOptionBottomSheet.TAG)
    }

    override fun onStartRoutineClicked() {
        // do nothing
    }

    override fun onPlusClicked(position: Int) {
        // do nothing
    }

    override fun onItemClicked(position: Int, workout: Workout) {
        // do nothing
    }
}
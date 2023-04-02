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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.FragmentWorkoutListBinding
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.model.WorkoutListResponse
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.utils.setTextWithVisibility
import com.aslifitness.fitracker.widgets.ItemOffsetDecoration
import com.aslifitness.fitracker.widgets.searchbar.FitnessSearchCallback

/**
 * @author Shubham Pandey
 */
class WorkoutListFragment: Fragment(), WorkoutListAdapterCallback {

    private lateinit var binding: FragmentWorkoutListBinding
    private lateinit var viewModel: WorkoutListViewModel
    private var callback: WorkoutFragmentCallback? = null
    private var title: String? = null

    companion object {
        const val TAG = "WorkoutListFragment"
        private const val TITLE = "title"

        fun newInstance(title: String?) = WorkoutListFragment().apply {
            arguments = bundleOf(Pair(TITLE, title))
        }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { title = it.getString(TITLE) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWorkoutListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInitialView()
        setupViewModel()
    }

    private fun setupInitialView() {
        binding.toolbar.title = title
        binding.searchBar.setLeftIcon(R.drawable.ic_search_24)
        binding.searchBar.addOnTextChange(object : FitnessSearchCallback {
            override fun onTextChanged(text: String) {
                (binding.searchWorkouts.adapter as? WorkoutListAdapter)?.filter?.filter(text)
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, WorkoutListViewModelFactory())[WorkoutListViewModel::class.java]
        viewModel.fetchWorkoutList()
        viewModel.workoutListViewState.observe(viewLifecycleOwner) { onViewStateChanged(it) }
    }

    private fun onViewStateChanged(state: NetworkState<ApiResponse<WorkoutListResponse>>?) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Error -> showError(state.throwable)
            is NetworkState.Success -> handlerResponse(state.data)
            else -> {
                // do nothing..
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
                configureDivider()
                binding.searchWorkouts.visibility = View.VISIBLE
            } else {
                binding.searchWorkouts.visibility = View.GONE
            }
        }
    }

    private fun configureDivider() {
        val divider = ContextCompat.getDrawable(requireContext(), R.drawable.line_divider)
        val dimen18Dp = requireContext().resources.getDimension(R.dimen.dimen_18dp)
        divider?.let {
            binding.searchWorkouts.addItemDecoration(ItemOffsetDecoration(dimen18Dp.toInt(), it, RecyclerView.VERTICAL))
        }
    }

    override fun onItemClicked(item: Workout) {
        item.header?.let { callback?.onWorkoutSelected(it) }
    }

    private fun showError(throwable: Throwable) {
        binding.loader.visibility = View.GONE
    }

    private fun showLoader() {
        binding.loader.visibility = View.VISIBLE
    }
}

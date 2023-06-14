package com.aslifitness.fitracker.home

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitracker.auth.UserAuthActivity
import com.aslifitness.fitracker.databinding.FragmentHomeBinding
import com.aslifitness.fitracker.firebase.FBAuthUtil
import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.model.WorkoutDto
import com.aslifitness.fitracker.model.WorkoutResponse
import com.aslifitness.fitracker.network.ApiHandler
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.profile.UserProfileActivity
import com.aslifitness.fitracker.utils.ZERO
import com.aslifitness.fitracker.utils.setImageWithVisibility
import com.aslifitness.fitracker.utils.setTextWithVisibility
import com.aslifitness.fitracker.workoutlist.WorkoutListActivity
import javax.inject.Inject

/**
 * @author Shubham Pandey
 */
class HomeFragment : Fragment(), WorkOutAdapterCallback {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var viewModel: HomeViewModel

    companion object {
        internal const val TAG = "HomeFragment"
        private const val SPAN_COUNT = 2
        private const val PADDING = 36
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        binding.profileImage.setOnClickListener {
            if (FBAuthUtil.isUserAuthenticated()) {
                startActivity(Intent(requireActivity(), UserProfileActivity::class.java))
            } else {
                startActivity(Intent(requireActivity(), UserAuthActivity::class.java))
            }
        }
    }

    private fun setupViewModel() {
        val factory = HomeViewModelFactory(HomeRepository(ApiHandler.apiService))
        viewModel = ViewModelProvider(viewModelStore, factory)[HomeViewModel::class.java]
        viewModel.getWorkoutList()
        viewModel.homeViewState.observe(viewLifecycleOwner) { onHomeViewStateChanged(it) }
    }

    private fun onHomeViewStateChanged(state: NetworkState<ApiResponse<WorkoutResponse>>) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Error -> hideLoader()
            is NetworkState.Success -> handleResponse(state.data)
        }
    }

    private fun handleResponse(data: ApiResponse<WorkoutResponse>?) {
        data?.data?.run {
            binding.greeting.setTextWithVisibility(header)
            binding.date.setTextWithVisibility(subHeader)
            configureUser(userDto)
            configureWorkoutList(items)
        }
    }

    private fun configureUser(userDto: UserDto?) {
        userDto?.run { binding.profileImage.setImageWithVisibility(profileImage) }
    }

    private fun configureWorkoutList(workoutList: List<WorkoutDto>?) {
        hideLoader()
        if (!workoutList.isNullOrEmpty()) {
            val gridLayoutManager = GridLayoutManager(requireContext(), SPAN_COUNT, RecyclerView.VERTICAL, false)
            binding.workoutList.layoutManager = gridLayoutManager
            val workoutAdapter = WorkoutListAdapter(workoutList, this)
            binding.workoutList.adapter = workoutAdapter
            binding.workoutList.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val childIndex = parent.indexOfChild(view)
                    if (childIndex in workoutList.count() - SPAN_COUNT..workoutList.count()) {
                        outRect.bottom = ZERO
                    } else {
                        outRect.bottom = PADDING
                    }
                    if (childIndex  % 2 == 0) {
                        outRect.right = PADDING
                    }
                }
            })
        }
    }

    private fun hideLoader() {
        binding.loader.visibility = View.GONE
    }

    private fun showLoader() {
        binding.loader.visibility = View.VISIBLE
    }

    override fun onWorkoutSelected(workoutDto: WorkoutDto) {
        workoutDto.title?.let { WorkoutListActivity.start(requireContext(), it) }
    }
}
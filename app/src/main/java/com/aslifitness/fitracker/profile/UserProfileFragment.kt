package com.aslifitness.fitracker.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.aslifitness.fitracker.HomeActivity
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.FragmentUserProfileBinding
import com.aslifitness.fitracker.detail.data.WorkoutHistory
import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.model.WorkoutDto
import com.aslifitness.fitracker.model.profile.ProfileDashboard
import com.aslifitness.fitracker.model.profile.UserProfileResponse
import com.aslifitness.fitracker.network.ApiHandler
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.sharedprefs.UserStore
import com.aslifitness.fitracker.utils.DeeplinkResolver
import com.aslifitness.fitracker.utils.setImageWithPlaceholder
import com.aslifitness.fitracker.utils.setTextWithVisibility
import com.aslifitness.fitracker.widgets.SpaceItemDecoration
import com.aslifitness.fitracker.widgets.WorkoutHistoryCallback
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * @author Shubham Pandey
 */
class UserProfileFragment: Fragment(), DashboardAdapterCallback, WorkoutHistoryCallback {

    private lateinit var binding: FragmentUserProfileBinding
    private val viewModel: UserProfileViewModel by viewModels { ProfileViewModelFactory(ApiHandler.apiService) }

    companion object {
        const val TAG = "ProfileFragment"
        fun newInstance() = UserProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViewModel()
    }

    private fun setupToolbar() {
        binding.profileToolbar.title = UserStore.getUserDetail()?.name
        binding.profileToolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        binding.signOut.setOnClickListener { Firebase.auth.signOut() }
        Firebase.auth.addAuthStateListener {
            if (it.currentUser != null) {
                // signed in
            } else {
                startActivity(Intent(activity, HomeActivity::class.java))
            }
        }
    }

    private fun setupViewModel() {
        val userId = UserStore.getUserId()
        if (userId.isNotEmpty()) viewModel.fetchUserProfile(userId)
        viewModel.userProfileViewState.observe(viewLifecycleOwner) { onViewStateChanged(it) }
    }

    private fun onViewStateChanged(state: NetworkState<ApiResponse<UserProfileResponse>>?) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Error -> showError(state.throwable)
            is NetworkState.Success -> handleResponse(state.data)
            else -> {
                // do nothing.
            }
        }
    }

    private fun handleResponse(data: ApiResponse<UserProfileResponse>?) {
        binding.contentLoader.hide()
        data?.data?.run {
            configureProfile(profile)
            configureDashboard(dashboard)
            configureWorkoutHistory(workouts)
        }
    }

    private fun configureDashboard(dashboard: ProfileDashboard?) {
        if (dashboard?.items.isNullOrEmpty()) return
        binding.profileCards.run {
            layoutManager = GridLayoutManager(context, 2)
            adapter = DashboardAdapter(dashboard?.items!!, this@UserProfileFragment)
            addItemDecoration(SpaceItemDecoration(12))
        }
    }

    private fun configureWorkoutHistory(workouts: WorkoutHistory?) {
        workouts?.let {
            binding.workoutHistory.setData(it, this)
            binding.workoutHistory.visibility = View.VISIBLE
        }
    }

    private fun configureProfile(profile: UserDto?) {
        binding.userName.setTextWithVisibility(profile?.name)
        binding.icProfile.setImageWithPlaceholder(profile?.profileImage, R.drawable.ic_user_circle_24)
    }

    private fun showError(throwable: Throwable) {
        binding.contentLoader.hide()
    }

    private fun showLoader() {
        binding.contentLoader.show()
    }

    override fun onItemClicked(workoutDto: WorkoutDto) {
        DeeplinkResolver.resolve(requireActivity(), workoutDto.actionUrl)
    }

    override fun onDropDownClicked() {
        // do nothing.
    }

    override fun onPlusClicked() {
        // do nothing.
    }
}
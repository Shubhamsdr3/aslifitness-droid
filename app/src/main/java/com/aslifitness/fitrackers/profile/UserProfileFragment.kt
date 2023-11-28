package com.aslifitness.fitrackers.profile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.auth.UserAuthActivity
import com.aslifitness.fitrackers.databinding.FragmentUserProfileBinding
import com.aslifitness.fitrackers.db.AppDatabase
import com.aslifitness.fitrackers.detail.data.WorkoutHistory
import com.aslifitness.fitrackers.model.UserDto
import com.aslifitness.fitrackers.model.WorkoutDto
import com.aslifitness.fitrackers.model.profile.ProfileDashboard
import com.aslifitness.fitrackers.model.profile.UserProfileResponse
import com.aslifitness.fitrackers.network.ApiHandler
import com.aslifitness.fitrackers.network.ApiResponse
import com.aslifitness.fitrackers.network.NetworkState
import com.aslifitness.fitrackers.sharedprefs.UserStore
import com.aslifitness.fitrackers.utils.DeeplinkResolver
import com.aslifitness.fitrackers.utils.setTextWithVisibility
import com.aslifitness.fitrackers.utils.show
import com.aslifitness.fitrackers.widgets.SpaceItemDecoration
import com.aslifitness.fitrackers.widgets.WorkoutHistoryCallback
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import timber.log.Timber
import java.util.Random

/**
 * @author Shubham Pandey
 */
class UserProfileFragment: Fragment(), DashboardAdapterCallback, WorkoutHistoryCallback, EditProfileBottomSheetCallback {

    private lateinit var binding: FragmentUserProfileBinding
    private val userRepository by lazy { UserRepository(ApiHandler.apiService, AppDatabase.getInstance().userDao()) }
    private lateinit var viewModel: UserProfileViewModel
    private var userDto: UserDto? = null

    private val googleApiClient: GoogleApiClient by lazy {
        GoogleApiClient.Builder(requireContext())
            .enableAutoManage(requireActivity()) {}
            .addApi(Auth.GOOGLE_SIGN_IN_API)
            .build();
    }

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
        initView()
        setupListener()
        setupViewModel()
    }

    private fun setupListener() {
        binding.userName.setOnClickListener { openEditBottomSheet(getString(R.string.enter_your_name), userDto?.name) }
        binding.tvAge.setOnClickListener { openEditBottomSheet(getString(R.string.enter_your_age), userDto?.age) }
        binding.tvWeight.setOnClickListener { openEditBottomSheet(getString(R.string.enter_your_weight), userDto?.weight) }
//        binding.tvEdit.setOnClickListener {
//            startActivity(Intent(activity, OnboardingActivity::class.java))
//        }
    }

    override fun onStart() {
        super.onStart()
        googleApiClient.connect()
    }

    private fun initView() {
        binding.userName.setEditable(true)
        binding.tvAge.setEditable(true)
        binding.tvAge.setTitleIconResource(R.drawable.icon_age_80)
        binding.tvWeight.setEditable(true)
        binding.tvWeight.setTitleIconResource(R.drawable.icon_weight_50)
        binding.tvAge.setTitle(getString(R.string.age))
        binding.tvWeight.setTitle(getString(R.string.weight))
    }

    private fun setupToolbar() {
        binding.profileToolbar.title = UserStore.getUserDetail()?.name
        binding.profileToolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        binding.tvSignout.setOnClickListener { viewModel.signOut(googleApiClient) }
        setVersionName()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(viewModelStore, ProfileViewModelFactory(userRepository))[UserProfileViewModel::class.java]
        val uId = UserStore.getUId()
        if (uId.isNotEmpty()) viewModel.fetchUserProfile(uId)
        viewModel.userProfileNetworkState.observe(viewLifecycleOwner) { onViewStateChanged(it) }
        viewModel.userProfileViewState.observe(viewLifecycleOwner) { onProfileViewStateChanged(it) }
    }

    private fun onProfileViewStateChanged(state: UserProfileViewState?) {
        when(state) {
            is UserProfileViewState.OnUserLogout -> onUserLogout()
            else -> {
                // do nothing
            }
        }
    }

    private fun onUserLogout() {
        startActivity(Intent(activity, UserAuthActivity::class.java))
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
        binding.showLoader = false
        binding.executePendingBindings()
        data?.data?.run {
            configureProfile(profile)
//            configureDashboard(dashboard)
//            configureWorkoutHistory(workouts)
        }
    }

    private fun setVersionName() {
        val packageManager = requireActivity().packageManager
        try {
            // Get the package information
            val packageInfo = packageManager.getPackageInfo(requireActivity().packageName, 0)
            val versionName = packageInfo.versionName
            val versionCode = packageInfo.versionCode
            binding.appVersion.text = "v$versionName/$versionCode"
        } catch (ex: Exception) {
            Timber.d(TAG, ex)
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
        this.userDto = profile
        binding.userName.setTitleIconResource(R.drawable.icon_user_name)
        binding.userName.setTitle(getString(R.string.name))
        binding.userName.setSubtitle(profile?.name)
        viewModel.showUserDetail()
        setUserHandle(profile?.userId)
        binding.tvSignout.setTitleIconResource(R.drawable.icon_logout_24)
        binding.tvSignout.setSubtitle(getString(R.string.log_out))
        binding.tvPhone.setTitleIconResource(R.drawable.icon_phone_24)
        binding.tvPhone.setTitle(getString(R.string.phone_number))
        binding.tvPhone.setSubtitle(profile?.phoneNumber)
        binding.tvPhone.setEditable(false)
        binding.tvAge.setSubtitle(profile?.age)
        binding.tvWeight.setSubtitle(profile?.weight)
        configureAvatar(profile?.name, profile?.profileImage)
    }

    private fun setUserHandle(userId: String?) {
        if (userId.isNullOrEmpty()) return
        binding.tvHandle.setTextWithVisibility("@${userId}")
    }

    private fun openEditBottomSheet(title: String, name: String?) {
        EditProfileDetailBottomSheet.newInstance(title, name).show(childFragmentManager, EditProfileDetailBottomSheet.TAG)
    }

    private fun configureAvatar(name: String?, imageUrl: String?) {
        val random = Random()
        val color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
        val firstCharacter = name?.substring(0, 1) ?: ""
        val image = AvatarGenerator.AvatarBuilder(requireContext())
            .setLabel(firstCharacter)
            .setAvatarSize(240)
            .setTextSize(60)
            .toSquare()
            .toCircle()
            .setBackgroundColor(color)
            .build()
        Glide.with(this)
            .load(imageUrl)
            .placeholder(image)
            .into(binding.icProfile)
        binding.icProfile.show()
    }

    override fun onProfileUpdated(title: String?, textData: String) {
        if (title.isNullOrEmpty()) return
        title.let {
            if (it.contains("name", true)) {
                binding.userName.setSubtitle(textData)
                viewModel.updateUserName(textData)
            } else if (it.contains("age", true)) {
                binding.tvAge.setSubtitle(textData)
                viewModel.updateUserAge(textData.toInt())
            } else if(it.contains("weight", true)) {
                binding.tvWeight.setSubtitle(textData)
                viewModel.updateUserWeight(textData.toInt())
            } else {
                // do nothing
            }
        }
    }

    private fun showError(throwable: Throwable) {
        binding.showLoader = false
        binding.executePendingBindings()
    }

    private fun showLoader() {
        binding.showLoader = true
        binding.executePendingBindings()
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
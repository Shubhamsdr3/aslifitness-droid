package com.aslifitness.fitrackers.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
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
import com.aslifitness.fitrackers.profile.TakePictureActivity.Companion.PICTURE_URL
import com.aslifitness.fitrackers.profile.uploadworker.FileUploadWorker
import com.aslifitness.fitrackers.sharedprefs.UserStore
import com.aslifitness.fitrackers.utils.DeeplinkResolver
import com.aslifitness.fitrackers.utils.gone
import com.aslifitness.fitrackers.utils.setTextWithVisibility
import com.aslifitness.fitrackers.utils.show
import com.aslifitness.fitrackers.widgets.SpaceItemDecoration
import com.aslifitness.fitrackers.widgets.WorkoutHistoryCallback
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import timber.log.Timber
import java.io.File
import java.util.Random


/**
 * @author Shubham Pandey
 */
class UserProfileFragment: Fragment(), DashboardAdapterCallback, WorkoutHistoryCallback,
    EditProfileBottomSheetCallback, ChosePictureBottomSheetCallback {

    private lateinit var binding: FragmentUserProfileBinding
    private val userRepository by lazy { UserRepository(ApiHandler.apiService, AppDatabase.getInstance().userDao()) }
    private lateinit var viewModel: UserProfileViewModel
    private var userDto: UserDto? = null

    companion object {
        const val TAG = "ProfileFragment"
        private const val GALLERY_PERMISSION_CODE = 1995
        fun newInstance() = UserProfileFragment()
    }

    private val chosePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data
            Glide.with(this).clear(binding.icProfile)
            binding.icProfile.setImageURI(imageUri)
            val picturePath = getImagePath(imageUri!!)
            uploadImage(picturePath)
        }
    }

    private val googleApiClient: GoogleApiClient by lazy {
        GoogleApiClient.Builder(requireContext())
            .enableAutoManage(requireActivity()) {}
            .addApi(Auth.GOOGLE_SIGN_IN_API)
            .build();
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

        binding.icProfile.setOnClickListener {
            ChosePictureBottomSheet.newInstance().show(childFragmentManager, ChosePictureBottomSheet.TAG)
        }
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

    @SuppressLint("NewApi")
    private fun uploadImage(imagePath: String?) {
        if (imagePath == null) return
        val fileName = "${UserStore.getUId()}.jpg"
        val data = Data.Builder()
            .putString(FileUploadWorker.IMAGE_PATH, imagePath)
            .putString(FileUploadWorker.FILE_NAME, fileName)
            .build()

        val uploadWorkRequest =
            OneTimeWorkRequestBuilder<FileUploadWorker>()
                .setInputData(data)
                .build()
        WorkManager.getInstance(requireContext()).apply {
            getWorkInfoByIdLiveData(uploadWorkRequest.id)
                .observe(viewLifecycleOwner) { workInfo ->
                    if (workInfo != null) {
                        if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                            val downloadUrl = workInfo.outputData.getString(FileUploadWorker.DOWNLOAD_URL)
                            viewModel.updateUserProfile(downloadUrl)
                            binding.icProfile.alpha = 1f
                            binding.circularProgress.gone()
                        } else if (workInfo.state ==WorkInfo.State.RUNNING) {
                            binding.icProfile.alpha = 0.5f
                            val value = workInfo.progress.getInt(FileUploadWorker.PROGRESS, 0)
                            binding.circularProgress.setProgress(value, true)
                            binding.circularProgress.show()
                        }
                    }
                }
        }.enqueue(uploadWorkRequest)
    }

    private fun getImagePath(imageUri: Uri): String? {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = activity?.contentResolver?.query(imageUri, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex: Int = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(columnIndex)
            }
            cursor.close()
        }
        return result
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUrl = result.data?.getStringExtra(PICTURE_URL)
            uploadImage(imageUrl)
            setUpProfilePicture(imageUrl)
        }
    }

    private fun setUpProfilePicture(imageUrl: String?) {
        Glide.with(this).clear(binding.icProfile)
        val bitmap = BitmapFactory.decodeFile(imageUrl)
        binding.icProfile.setImageBitmap(bitmap)
        viewModel.updateUserProfile(imageUrl ?: "")
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
            .setAvatarSize(260)
            .setTextSize(64)
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
    override fun onCameraClicked() {
        takePictureLauncher.launch(Intent(activity, TakePictureActivity::class.java))
    }

    override fun onGalleryClicked() {
        openGallery()
        if (isPermissionGranted()) {
            openGallery()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                GALLERY_PERMISSION_CODE
            )
        }
    }

    private fun openGallery() {
        Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }.also {
            chosePictureLauncher.launch(it)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GALLERY_PERMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            Toast.makeText(requireContext(), "Please grant the request...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDropDownClicked() {
        // do nothing.
    }

    override fun onPlusClicked() {
        // do nothing.
    }
}
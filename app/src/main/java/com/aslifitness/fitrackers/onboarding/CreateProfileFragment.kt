package com.aslifitness.fitrackers.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitrackers.databinding.FragmentCreateProfileBinding
import com.aslifitness.fitrackers.db.AppDatabase
import com.aslifitness.fitrackers.model.UserDto
import com.aslifitness.fitrackers.network.ApiHandler
import com.aslifitness.fitrackers.network.ApiResponse
import com.aslifitness.fitrackers.network.NetworkState
import com.aslifitness.fitrackers.profile.UserProfileActivity
import com.aslifitness.fitrackers.profile.UserRepository
import com.aslifitness.fitrackers.sharedprefs.UserStore

/**
 * Created by shubhampandey
 */
class CreateProfileFragment: Fragment() {

    private lateinit var binding: FragmentCreateProfileBinding

    private lateinit var viewModel: CreateProfileViewModel

    private val updatedDetails = mutableMapOf<String, String>()

    companion object {

        const val TAG = "CreateProfileFragment"
        fun newInstance() = CreateProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initListener()
    }

    private fun initListener() {
        binding.ivProfile.setOnClickListener {
            //TODO: fixme
        }

        binding.etInputId.addTextChangedListener {
            if (!it.isNullOrEmpty()) {
                updatedDetails["userId"] = it.toString()
            }
        }

        binding.etInputName.addTextChangedListener {
            if (!it.isNullOrEmpty()) {
                updatedDetails["name"] = it.toString()
            }
        }

        binding.etInputAge.addTextChangedListener {
            if (!it.isNullOrEmpty()) {
                updatedDetails["age"] = it.toString()
            }
        }

        binding.etInputWeight.addTextChangedListener {
            if (!it.isNullOrEmpty()) {
                updatedDetails["weight"] = it.toString()
            }
        }

        binding.btProfile.setOnClickListener {
            updatedDetails["id"] = UserStore.getUId()
            viewModel.updateUserProfile(updatedDetails)
        }
    }

    private fun initViewModel() {
        val factory = CreateProfileViewModelFactory(UserRepository(ApiHandler.apiService, AppDatabase.getInstance().userDao()))
        viewModel = ViewModelProvider(viewModelStore, factory)[CreateProfileViewModel::class.java]
        viewModel.createUserProfileNetworkState.observe(viewLifecycleOwner) { onCreateProfileNetworkStateChanged(it) }
    }

    private fun onCreateProfileNetworkStateChanged(state: NetworkState<ApiResponse<UserDto>>?) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Error -> showError(state.throwable)
            is NetworkState.Success -> handleSuccessResponse(state.data)
            else -> {
                // do nothing
            }
        }
    }

    private fun handleSuccessResponse(data: ApiResponse<UserDto>?) {
        binding.btProfile.hideLoader()
        data?.data?.let { viewModel.updateUserInDb(it) }
        val intent = Intent(activity, UserProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun showError(throwable: Throwable) {
        binding.btProfile.hideLoader()
    }

    private fun showLoader() {
        binding.btProfile.showLoader()
    }
}
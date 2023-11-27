package com.aslifitness.fitracker.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.HomeActivity
import com.aslifitness.fitracker.databinding.FragmentCreateProfileBinding
import com.aslifitness.fitracker.db.AppDatabase
import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.network.ApiHandler
import com.aslifitness.fitracker.network.ApiResponse
import com.aslifitness.fitracker.network.NetworkState
import com.aslifitness.fitracker.profile.UserRepository
import com.aslifitness.fitracker.sharedprefs.UserStore
import com.aslifitness.fitracker.utils.hide
import com.google.firebase.firestore.auth.User

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
        startActivity(Intent(activity, HomeActivity::class.java))
    }

    private fun showError(throwable: Throwable) {
        binding.btProfile.hideLoader()
    }

    private fun showLoader() {
        binding.btProfile.showLoader()
    }
}
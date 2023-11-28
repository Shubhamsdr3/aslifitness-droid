package com.aslifitness.fitrackers.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import com.aslifitness.fitrackers.databinding.BottomSheetEditProfileBinding
import com.aslifitness.fitrackers.model.UserDto
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by shubhampandey
 */
class EditProfileBottomSheet: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetEditProfileBinding
    private val user: UserDto? by lazy { arguments?.getParcelable(USER_DETAIL) }
    companion object {

        const val TAG = "EditProfileBottomSheet"
        private const val USER_DETAIL = "user_detail"
        fun newInstance(userDetail: UserDto) = EditProfileBottomSheet().apply {
            arguments = bundleOf(Pair(USER_DETAIL, userDetail))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomSheetEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListener()
    }

    private fun setupView() {
        user?.apply {
            binding.etInputName.setText(name)
            binding.etInputId.setText(userId)
            binding.etInputAge.setText(age)
            binding.etInputWeight.setText(weight)
        }
    }

    private fun setupListener() {
        binding.etInputName.addTextChangedListener {

        }

        binding.etInputId.addTextChangedListener {

        }

        binding.etInputAge.addTextChangedListener {

        }

        binding.etInputWeight.addTextChangedListener {

        }
    }
}
package com.aslifitness.fitracker.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.BottomDialogEditProfileBinding
import com.aslifitness.fitracker.db.AppDatabase
import com.aslifitness.fitracker.network.ApiHandler
import com.aslifitness.fitracker.utils.EMPTY
import com.aslifitness.fitracker.utils.Utility
import com.aslifitness.fitracker.utils.setTextWithVisibility
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by shubhampandey
 */
class EditProfileDetailBottomSheet: BottomSheetDialogFragment() {

    private lateinit var binding: BottomDialogEditProfileBinding

    private lateinit var viewModel: EditProfileViewModel

    private var callback: EditProfileBottomSheetCallback? = null

    private val userRepository by lazy { UserRepository(ApiHandler.apiService, AppDatabase.getInstance().userDao()) }

    private var textData: String? = null

    private val title: String? by lazy { arguments?.getString(TITLE, EMPTY) }
    companion object {

        const val TAG = "EditProfileDetailBottomSheet"

        private const val DATA = "data"
        private const val TITLE = "title"
        fun newInstance(title: String, text: String?) = EditProfileDetailBottomSheet().apply {
            arguments = bundleOf(
                Pair(TITLE, title),
                Pair(DATA, text)
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EditProfileBottomSheetCallback) {
            this.callback = context
        } else if (parentFragment != null && parentFragment is EditProfileBottomSheetCallback) {
            this.callback = parentFragment as EditProfileBottomSheetCallback
        } else {
            throw IllegalStateException("$context must implement $callback")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomDialogEditProfileBinding.inflate(inflater, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractExtras()
        configureListener()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(viewModelStore, EditProfileViewModelFactory(userRepository))[EditProfileViewModel::class.java]
    }

    private fun extractExtras() {
        textData = arguments?.getString(DATA, "")
        if (!textData.isNullOrEmpty()) {
            binding.etTitle.setText(textData)
        }
        binding.tvTitle.setTextWithVisibility(title)
        binding.tvSave.text = getString(R.string.save)
        binding.tvCancel.text = getString(R.string.cancel_text)
        binding.etTitle.requestFocus()
        Utility.showKeyboard(binding.etTitle)
    }

    private fun configureListener() {
        binding.tvCancel.setOnClickListener { dismissAllowingStateLoss() }
        binding.tvSave.setOnClickListener {
            if (!textData.isNullOrEmpty()) {
                callback?.onProfileUpdated(title, textData!!)
                dismissAllowingStateLoss()
            } else {
                Toast.makeText(requireContext(), "Name can't be empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.etTitle.addTextChangedListener {
            it?.let {
                textData = it.toString()
            }
        }
    }
}
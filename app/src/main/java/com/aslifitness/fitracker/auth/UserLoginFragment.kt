package com.aslifitness.fitracker.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aslifitness.fitracker.databinding.FragmentUserLoginBinding

/**
 * @author Shubham Pandey
 */
class UserLoginFragment : Fragment() {

    private lateinit var binding: FragmentUserLoginBinding
    private var callback: UserLoginFragmentCallback? = null

    companion object {
        const val TAG = "UserLoginFragment"
        fun newInstance() = UserLoginFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment != null && parentFragment is UserLoginFragmentCallback) {
            this.callback = parentFragment as UserLoginFragmentCallback
        } else if (context is UserLoginFragmentCallback) {
            this.callback = context
        } else {
            throw IllegalStateException("$context must implement fragment context")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener() {
        binding.loginLayout.submitButton.setOnClickListener { onSubmitClicked() }
    }

    private fun onSubmitClicked() {
        val phoneText = binding.loginLayout.phoneNumber.text
        if (!phoneText.isNullOrEmpty()) {
            val phoneNumber = phoneText.toString()
            callback?.onSubmitClicked(phoneNumber)
        }
    }
}
package com.aslifitness.fitracker.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.aslifitness.fitracker.databinding.FragmentUserLoginBinding
import com.aslifitness.fitracker.widgets.auth.SignInListener
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.HintRequest


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

    private val hintLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result: ActivityResult? ->
        if (result != null && result.data != null) {
            val data = result.data
            val credential = data?.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
            var phoneNum = credential?.id
            if (!phoneNum.isNullOrEmpty() && phoneNum.contains("+91")){
                phoneNum = phoneNum.replace("+91", "")
                binding.loginCardview.setPhoneNumber(phoneNum)
            }
        }
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
        requestPhoneNumberHint()
    }

    private fun requestPhoneNumberHint() {
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val intent = Credentials.getClient(requireActivity()).getHintPickerIntent(hintRequest)
        val intentSenderRequest = IntentSenderRequest.Builder(intent.intentSender)
        hintLauncher.launch(intentSenderRequest.build())
    }

    private fun setupListener() {
        binding.loginCardview.setSigInCallback(object : SignInListener {
            override fun onPhoneNumberEntered(phoneNumber: String) {
                callback?.onSubmitClicked(phoneNumber)
            }
        })
    }
}
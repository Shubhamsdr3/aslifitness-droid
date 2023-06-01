package com.aslifitness.fitracker.auth.otpverification

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aslifitness.fitracker.HomeActivity
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.auth.OTPReceiveListener
import com.aslifitness.fitracker.auth.SMSReceiver
import com.aslifitness.fitracker.databinding.FragmentOtpVerificationBinding
import com.aslifitness.fitracker.firebase.FBAuthUtil
import com.aslifitness.fitracker.model.UserDto
import com.aslifitness.fitracker.network.ApiHandler
import com.aslifitness.fitracker.sharedprefs.UserStore
import com.aslifitness.fitracker.utils.Utility
import com.aslifitness.fitracker.utils.setTextWithVisibility
import com.aslifitness.fitracker.utils.setUnderlineText
import com.aslifitness.fitracker.widgets.auth.otpinput.OTPListener
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * @author Shubham Pandey
 */
class OtpVerificationFragment: Fragment(), OTPListener {

    private val viewModel: OtpVerificationViewModel by viewModels { OtpVerificationViewModelFactory(ApiHandler.apiService) }

    private lateinit var binding: FragmentOtpVerificationBinding
    private var phoneNumber: String? = null
    private var phoneVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    private var intentFilter: IntentFilter? = null
    private var smsReceiver: SMSReceiver? = null

    companion object {
        const val TAG = "OtpVerificationFragment"
        private const val PHONE_NUMBER = "phone_number"

        fun newInstance(phoneNumber: String) = OtpVerificationFragment().apply {
            arguments = bundleOf(Pair(PHONE_NUMBER, phoneNumber))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOtpVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractExtras()
        configureView()
        setupObserver()
        configureOTPVerification()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(smsReceiver, intentFilter)
    }

    private fun configureOTPVerification() {
        initSmaRetriever()
        initBroadCast()
    }

    private fun initBroadCast() {
        intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        smsReceiver = SMSReceiver()
        smsReceiver?.setOTPListener(object : OTPReceiveListener {
            override fun onOTPReceived(otp: String?) {
                if (!otp.isNullOrEmpty()) {
                    binding.otpInput.setOTP(otp)
                }
            }
        })
    }

    private fun initSmaRetriever() {
        val client = SmsRetriever.getClient(requireActivity())
        val task: Task<Void> = client.startSmsRetriever()
        task.addOnSuccessListener {

        }

        task.addOnFailureListener {

        }
    }

    private fun setupObserver() {
        viewModel.otpViewState.observe(viewLifecycleOwner) { onViewStateChanged(it) }
    }

    private fun onViewStateChanged(state: OtpVerificationViewState?) {
        when(state) {
            is OtpVerificationViewState.ShowLoader -> showLoader()
            is OtpVerificationViewState.HideLoader -> hideLoader()
            is OtpVerificationViewState.OnUserSaved -> onUserSaved()
            is OtpVerificationViewState.ShowKeyboard -> showKeyBoard()
            else -> {
                // do nothing
            }
        }
    }

    private fun onUserSaved() {
        binding.otpSubmit.hideLoader()
        startActivity(Intent(activity, HomeActivity::class.java))
    }

    private fun hideLoader() {
        binding.otpSubmit.hideLoader()
    }

    private fun showLoader() {
        binding.otpSubmit.showLoader()
    }

    private fun showKeyBoard() {
        Utility.showKeyboard(binding.otpInput)
    }

    private fun extractExtras() {
        phoneNumber = arguments?.getString(PHONE_NUMBER)
        phoneNumber?.let { verifyPhoneNumber() }
    }

    private fun configureView() {
        binding.header.text = getString(R.string.otp_text)
        binding.subHeader.setTextWithVisibility(phoneNumber)
        binding.resendTxt.setUnderlineText(getString(R.string.resend_otp))
        binding.changeText.setUnderlineText(getString(R.string.change))
        initOTPInput()
        setupListener()
    }

    private fun setupListener() {
        binding.changeText.setOnClickListener { activity?.onBackPressed() }
        binding.otpSubmit.setOnClickListener { onOtpSubmitClicked() }
        binding.resendTxt.setOnClickListener { verifyPhoneNumber() }
        binding.otpInput.setCallback(this)
    }

    private fun onOtpSubmitClicked() {
        if (!phoneVerificationId.isNullOrEmpty() && binding.otpInput.otp.isNotEmpty()) {
            val credential = PhoneAuthProvider.getCredential(phoneVerificationId!!, binding.otpInput.otp)
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun initOTPInput() {
        binding.otpInput.run {
            visibility = View.VISIBLE
            setHeader(getString(R.string.otp_label))
            getInputField()?.imeOptions = EditorInfo.IME_ACTION_DONE
            handleOTPImeAction()
            setInitialState()
            requestFocusOTP()
        }
    }

    private fun handleOTPImeAction() {
        val otpEditText = binding.otpInput.getInputField()
        otpEditText?.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onContinueClick()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun onContinueClick() {
        viewModel.onContinueClick(binding.otpInput.otp)
    }

    private fun verifyPhoneNumber() {
        if (phoneNumber.isNullOrEmpty()) return
        val options = PhoneAuthOptions.newBuilder(FBAuthUtil.getFirebaseAuth())
            .setPhoneNumber("$+91$phoneNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(ex: FirebaseException) {
                    if (ex is FirebaseAuthInvalidCredentialsException) {
                        Timber.e(ex) // Invalid request
                    } else if (ex is FirebaseTooManyRequestsException) {
                        Timber.e(ex) // The SMS quota for the project has been exceeded
                    }
                }

                override fun onCodeAutoRetrievalTimeOut(verificationId: String) {
                    phoneVerificationId = verificationId
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    phoneVerificationId = verificationId
                    resendToken = token
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FBAuthUtil.getFirebaseAuth().signInWithCredential(credential)
            .addOnCompleteListener {  task ->
                if (task.isSuccessful) {
                    val firebaseUser = task.result.user
                    if (firebaseUser != null) {
                        createUser(firebaseUser)
                    } else {
                        Toast.makeText(requireContext(), "Sign In failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Timber.e(task.exception)
                        binding.otpInput.showError()
                    }
                }
            }
    }

    private fun createUser(user: FirebaseUser) {
        val userDto = UserDto(
            userId = user.uid,
            name = user.displayName,
            profileImage = user.photoUrl.toString(),
            phoneNumber = user.phoneNumber
        )
        UserStore.putUserDetail(userDto)
        UserStore.putUserId(user.uid)
        viewModel.saveUser(userDto)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(smsReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        smsReceiver = null
    }

    override fun onOTPComplete(otp: String) {
        onOtpSubmitClicked()
    }
}
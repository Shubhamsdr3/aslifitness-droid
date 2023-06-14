package com.aslifitness.fitracker.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.auth.otpverification.OtpVerificationActivity
import com.aslifitness.fitracker.databinding.ActivityUserAuthBinding
import com.aslifitness.fitracker.utils.INTENT_PHONE

/**
 * @author Shubham Pandey
 */
class UserAuthActivity: AppCompatActivity(), UserLoginFragmentCallback {

    private lateinit var binding: ActivityUserAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.authToolbar.setNavigationOnClickListener { onBackPressed() }
        loadFragment(savedInstanceState)
    }

    private fun loadFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.auth_container, UserLoginFragment.newInstance(), UserLoginFragment.TAG)
            }.commitAllowingStateLoss()
        }
    }

    override fun onSubmitClicked(phoneNumber: String) {
        Intent(this, OtpVerificationActivity::class.java).apply {
            putExtra(INTENT_PHONE, phoneNumber)
        }.also {
            startActivity(it)
        }
    }
}
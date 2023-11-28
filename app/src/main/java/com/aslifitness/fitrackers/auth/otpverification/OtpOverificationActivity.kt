package com.aslifitness.fitrackers.auth.otpverification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.ActivityOtpVerificationBinding
import com.aslifitness.fitrackers.utils.EMPTY
import com.aslifitness.fitrackers.utils.INTENT_PHONE
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Shubham Pandey
 */

@AndroidEntryPoint
class OtpVerificationActivity: AppCompatActivity() {

    private lateinit var binding: ActivityOtpVerificationBinding
    private var phoneNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        extractExtras()
        configureToolbar()
        loadFragment(savedInstanceState)
    }

    private fun configureToolbar() {
        setSupportActionBar(binding.otpToolbar)
        binding.otpToolbar.title = EMPTY
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        binding.otpToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun extractExtras() {
        phoneNumber = intent.getStringExtra(INTENT_PHONE)
    }

    private fun loadFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) addFragment()
    }

    private fun addFragment() {
        phoneNumber?.let {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.container, OtpVerificationFragment.newInstance(it))
            }.commitAllowingStateLoss()
        }
    }
}
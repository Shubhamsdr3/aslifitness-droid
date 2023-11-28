package com.aslifitness.fitrackers.uilib

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aslifitness.fitrackers.databinding.ActivityExampleBinding
import com.aslifitness.fitrackers.widgets.auth.otpinput.OTPListener

/**
 * @author Shubham Pandey
 */
class ExampleActivity: AppCompatActivity() {

    private lateinit var binding: ActivityExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.otpInput.setCallback(object : OTPListener {
            override fun onOTPComplete(otp: String) {
                Toast.makeText(this@ExampleActivity, "Otp completed...$otp", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
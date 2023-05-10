package com.aslifitness.fitracker.assistant.tracking

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.assistant.model.FitActivity
import com.aslifitness.fitracker.databinding.FitTrackingFragmentBinding
import com.aslifitness.fitracker.assistant.model.FitRepository
import java.util.concurrent.TimeUnit

/**
 * Fragment that handles the starting of an activity and tracks the status.
 *
 * When the fragments starts, it will start a countdown and launch the foreground service
 * that will keep track of the status.
 *
 * The view will observe the status and update its content.
 */
class FitTrackingFragment : Fragment() {

    companion object {
        /**
         * Parameter used when creating the fragment to add the type of activity.
         */
        const val PARAM_TYPE = "type"
    }

    lateinit var actionsCallback: FitTrackingActions

    private lateinit var binding: FitTrackingFragmentBinding

    private val fitRepository: FitRepository by lazy {
        FitRepository.getInstance(requireContext())
    }
    private val fitServiceIntent: Intent by lazy {
        Intent(requireContext(), FitTrackingService::class.java)
    }

    private var countDownMs = TimeUnit.SECONDS.toMillis(5)

    private val countDownTimer = object : CountDownTimer(countDownMs, 1000) {

        override fun onFinish() {
            countDownMs = 0
            startTrackingService()
        }

        override fun onTick(millisUntilFinished: Long) {
            countDownMs = millisUntilFinished

            val secondsLeft = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished).toString()
            binding.startActivityCountDown.text = secondsLeft
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FitTrackingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        observerOnGoingActivity()
    }

    private fun observerOnGoingActivity() {
        fitRepository.getOnGoingActivity().observe(viewLifecycleOwner) {
            if (it == null) {
                if (countDownMs > 0) {
                    countDownTimer.start()
                    onCountDown()
                }
            } else {
                onTracking(it)
            }
        }
    }

    private fun setupListener() {
        binding.startActivityButton.setOnClickListener {
            val fitActivity = fitRepository.getOnGoingActivity().value
            if (fitActivity == null) {
                startTrackingService()
            } else {
                requireActivity().stopService(fitServiceIntent)
                actionsCallback.onActivityStopped(fitActivity.id)
            }
        }
    }

    override fun onDestroyView() {
        countDownTimer.cancel()
        super.onDestroyView()
    }

    /**
     * Stop the countdown if running, and start a foreground service
     */
    private fun startTrackingService() {
        countDownMs = 0
        countDownTimer.cancel()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().startForegroundService(fitServiceIntent)
        } else {
            requireActivity().startService(fitServiceIntent)
        }
    }

    /**
     * Update the count down view
     */
    private fun onCountDown() {
        val type = arguments?.getSerializable(PARAM_TYPE) as? FitActivity.Type ?: FitActivity.Type.UNKNOWN
        binding.startActivityTitle.text = getString(R.string.start_activity_title, type.name.toLowerCase())
        binding.startActivityCountDown.text = TimeUnit.MILLISECONDS.toSeconds(countDownMs).toString()
        binding.startActivityButton.isSelected = false
    }

    /**
     * Update the tracking view
     */
    private fun onTracking(activity: FitActivity) {
        binding.startActivityTitle.setText(R.string.tracking_notification_title)
        binding.startActivityCountDown.text = getString(
            R.string.stats_tracking_distance,
            activity.distanceMeters.toInt()
        )
        binding.startActivityButton.isSelected = true
    }

    interface FitTrackingActions {
        /**
         * Called when the activity has stopped.
         */
        fun onActivityStopped(activityId: String)
    }
}

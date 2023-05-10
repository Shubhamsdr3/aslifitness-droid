package com.aslifitness.fitracker.assistant

import android.app.assist.AssistContent
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.assistant.BiiIntents.EXERCISE_NAME
import com.aslifitness.fitracker.assistant.BiiIntents.EXERCISE_TYPE
import com.aslifitness.fitracker.assistant.BiiIntents.START_EXERCISE
import com.aslifitness.fitracker.assistant.BiiIntents.STOP_EXERCISE
import com.aslifitness.fitracker.assistant.tracking.FitTrackingService
import com.aslifitness.fitracker.assistant.home.FitStatsFragment
import com.aslifitness.fitracker.assistant.model.FitActivity
import com.aslifitness.fitracker.assistant.model.FitRepository
import com.aslifitness.fitracker.assistant.tracking.FitTrackingFragment
import com.aslifitness.fitracker.databinding.FitActivityBinding
import org.json.JSONObject

/**
 * Main activity responsible for the app navigation and handling Android intents.
 */
class FitMainActivity : AppCompatActivity(), FitStatsFragment.FitStatsActions, FitTrackingFragment.FitTrackingActions {

    private lateinit var binding: FitActivityBinding

    companion object {
        private const val TAG = "FitMainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FitActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "======= Here ========= %s")

        // Logging for troubleshooting purposes
        logIntent(intent)

        // Handle the intent this activity was launched with.
        intent?.handleIntent()
    }

    /**
     * Handle new intents that are coming while the activity is on foreground since we set the
     * launchMode to be singleTask, avoiding multiple instances of this activity to be created.
     *
     * See [launchMode](https://developer.android.com/guide/topics/manifest/activity-element#lmode)
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.handleIntent()
    }


    /**
     * For debugging Android intents
     */
    private fun logIntent(intent: Intent) {
        val bundle: Bundle = intent.extras ?: return

        Log.d(TAG, "======= logIntent ========= %s")
        Log.d(TAG, "Logging intent data start")

        bundle.keySet().forEach { key ->
            Log.d(TAG, "[$key=${bundle.get(key)}]")
        }

        Log.d(TAG, "Logging intent data complete")
    }

    /**
     * When a fragment is attached add the required callback methods.
     */
    override fun onAttachFragment(fragment: Fragment) {
        when (fragment) {
            is FitStatsFragment -> fragment.actionsCallback = this
            is FitTrackingFragment -> fragment.actionsCallback = this
        }
    }

    /**
     * When the user invokes an App Action while in your app, users will see a suggestion
     * to share their foreground content.
     *
     * By implementing onProvideAssistContent(), you provide the Assistant with structured
     * information about the current foreground content.
     *
     * This contextual information enables the Assistant to continue being helpful after the user
     * enters your app.
     */
    override fun onProvideAssistContent(outContent: AssistContent) {
        super.onProvideAssistContent(outContent)

        // JSON-LD object based on Schema.org structured data
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // This is just an example, more accurate information should be provided
            outContent.structuredData = JSONObject()
                .put("@type", "ExerciseObservation")
                .put("name", "My last runs")
                .put("url", "https://fit-actions.firebaseapp.com/stats")
                .toString()
        }
    }

    /**
     * Callback method from the FitStatsFragment to indicate that the tracking activity flow
     * should be shown.
     */
    override fun onStartActivity() {
        updateFragment(
            newFragmentClass = FitTrackingFragment::class.java,
            arguments = Bundle().apply {
                putSerializable(FitTrackingFragment.PARAM_TYPE, FitActivity.Type.RUNNING)
            },
            toBackStack = true
        )
    }

    /**
     * Callback method when an activity stops.
     * We could show a details screen, for now just go back to home screen.
     */
    override fun onActivityStopped(activityId: String) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            updateFragment(FitStatsFragment::class.java)
        }
    }

    /**
     * Handles the action from the intent base on the type.
     *
     * @receiver the intent to handle
     */
    private fun Intent.handleIntent() {
        when (action) {
            // When the BII is matched, Intent.Action_VIEW will be used
            Intent.ACTION_VIEW -> handleIntent(data)
            // Otherwise start the app as you would normally do.
            else -> showDefaultFragment()
        }
    }

    private fun handleIntent(data: Uri?) {
        val exerciseType = data?.getQueryParameter(EXERCISE_TYPE) ?: intent?.extras?.getString(EXERCISE_TYPE)
        val exerciseName = data?.getQueryParameter(EXERCISE_NAME) ?: intent?.extras?.getString(EXERCISE_NAME)
        navigate(exerciseType, exerciseName)
    }

    private fun navigate(exerciseType: String?, exerciseName: String?) {
        when (exerciseType) {
            START_EXERCISE -> {
                val type = FitActivity.Type.find(exerciseName)
                val arguments = Bundle().apply {
                    putSerializable(FitTrackingFragment.PARAM_TYPE, type)
                }
                updateFragment(FitTrackingFragment::class.java, arguments)
            }
            STOP_EXERCISE -> {
                stopService(Intent(this, FitTrackingService::class.java))
                updateFragment(FitStatsFragment::class.java)
            }
            else -> {
                showDefaultFragment()
            }
        }
    }

    /**
     * Show ongoing activity or stats if none
     */
    private fun showDefaultFragment() {
        val onGoing = FitRepository.getInstance(this).getOnGoingActivity().value
        val fragmentClass = if (onGoing != null) {
            FitTrackingFragment::class.java
        } else {
            FitStatsFragment::class.java
        }
        updateFragment(fragmentClass)
    }

    /**
     * Utility method to update the Fragment with the given arguments.
     */
    private fun updateFragment(newFragmentClass: Class<out Fragment>, arguments: Bundle? = null, toBackStack: Boolean = false) {
        val currentFragment = supportFragmentManager.fragments.firstOrNull()
        if (currentFragment != null && currentFragment::class.java == newFragmentClass) {
            return
        }

        val fragment = supportFragmentManager.fragmentFactory.instantiate(newFragmentClass.classLoader!!, newFragmentClass.name)
        fragment.arguments = arguments

        supportFragmentManager.beginTransaction().run {
            replace(R.id.fitActivityContainer, fragment)
            if (toBackStack) {
                addToBackStack(null)
            }
            commit()
        }
    }
}

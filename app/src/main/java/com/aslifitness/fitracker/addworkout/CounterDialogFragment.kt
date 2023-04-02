package com.aslifitness.fitracker.addworkout

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.FragmentCounterBinding
import com.aslifitness.fitracker.detail.data.Workout
import com.aslifitness.fitracker.utils.setTextWithVisibility


/**
 * @author Shubham Pandey
 */
class CounterDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentCounterBinding
    private lateinit var viewModel: CounterViewModel
    private var callback: CounterFragmentCallback? = null
    private var count: Int = 0
    private var position: Int = 0
    private var workout: Workout ?= null
    private var isNewSet: Boolean = false

    companion object {
        const val TAG = "CounterDialogFragment"
        private const val POSITION = "position"
        private const val WORKOUT = "workout"
        private const val NEW_SET = "new_set"

        fun newInstance(position: Int, workout: Workout, isNewSet: Boolean): CounterDialogFragment {
            return CounterDialogFragment().apply {
                arguments = bundleOf(
                    Pair(POSITION, position),
                    Pair(WORKOUT, workout),
                    Pair(NEW_SET, isNewSet)
                )
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment != null && parentFragment is CounterFragmentCallback) {
            this.callback = parentFragment as CounterFragmentCallback
        } else if (context is CounterFragmentCallback) {
            this.callback = context
        } else {
            throw IllegalStateException("$context must implement fragment context")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractExtras()
        setupView()
        setupViewModel()
        setupListener()
    }

    private fun extractExtras() {
         arguments?.run {
             position = getInt(POSITION)
             workout = getParcelable(WORKOUT)
             isNewSet = getBoolean(NEW_SET)
             count = workout?.qtyInfo?.setCount ?: 0
             binding.title.setTextWithVisibility(workout?.header)
         }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, CounterViewModelFactory(count))[CounterViewModel::class.java]
        viewModel.counterLiveData.observe(viewLifecycleOwner) { count -> onCounterChanged(count) }
    }

    private fun onCounterChanged(count: Int?) {
        count?.let { binding.counter.text = it.toString() }
    }

    private fun setupView() {
        binding.counter.text = count.toString()
        binding.submitButton.text = getString(R.string.done)
    }

    private fun setupListener() {
        binding.increment.setOnClickListener { viewModel.onIncrementClicked() }
        binding.decrement.setOnClickListener { viewModel.onDecrementClicked() }
        binding.submitButton.setOnClickListener {
            callback?.onSubmitClicked(position, viewModel.getCount(), isNewSet)
            dismiss()
        }
    }

    override fun onResume() {
        val window: Window? = dialog?.window
        val size = Point()
        val display: Display? = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        window?.setLayout((size.x * 0.80).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
        super.onResume()
    }
}
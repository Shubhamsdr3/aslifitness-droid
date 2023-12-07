package com.aslifitness.fitrackers.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.BottomSheetDurationFilterBinding
import com.aslifitness.fitrackers.home.data.DurationFilterType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by shubhampandey
 */
class DurationFilterBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDurationFilterBinding

    private var callback: DurationFilterBottomSheetCallback? = null

    companion object {

        const val TAG = "DurationFilterBottomSheet"
        private const val FILTER_TYPE = "filter_type"
        fun newInstance(selectedType: Int) = DurationFilterBottomSheet().apply {
            arguments = bundleOf(Pair(FILTER_TYPE, selectedType))
        }
    }

    override fun getTheme() = R.style.BottomSheetDialogStyle
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment != null && parentFragment is DurationFilterBottomSheetCallback) {
            this.callback = parentFragment as DurationFilterBottomSheetCallback
        } else if (context is DurationFilterBottomSheetCallback) {
            this.callback = context
        } else {
            throw IllegalStateException("$context must implement callback")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomSheetDurationFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filterType = arguments?.getInt(FILTER_TYPE)
        setupView(filterType)
        setupListener()
    }

    private fun setupView(filterType: Int?) {
        when(filterType) {
            DurationFilterType.WEEK.code -> {
                binding.checkWeek.isChecked = true
            }

            DurationFilterType.MONTH.code -> {
                binding.checkMonth.isChecked = true
            }

            DurationFilterType.YEAR.code -> {
                binding.checkYear.isChecked = true
            }
        }
    }

    private fun setupListener() {
        binding.checkWeek.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                callback?.onDurationFilterClicked(DurationFilterType.WEEK.code)
                dismissAllowingStateLoss()
            }
        }
        binding.checkMonth.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                callback?.onDurationFilterClicked(DurationFilterType.MONTH.code)
                dismissAllowingStateLoss()
            }
        }

        binding.checkYear.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                callback?.onDurationFilterClicked(DurationFilterType.YEAR.code)
                dismissAllowingStateLoss()
            }
        }
    }
}
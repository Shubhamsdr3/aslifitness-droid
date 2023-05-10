package com.aslifitness.fitracker.routine.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aslifitness.fitracker.databinding.FragmentRoutineSummaryBinding
import javax.inject.Inject

/**
 * Created by shubhampandey
 */
class RoutineSummaryFragment: Fragment() {

    private lateinit var binding: FragmentRoutineSummaryBinding

    @Inject
    lateinit var viewModel: RoutineSummaryViewModel

    companion object {
        fun newInstance() = RoutineSummaryFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRoutineSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
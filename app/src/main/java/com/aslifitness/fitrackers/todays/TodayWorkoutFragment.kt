package com.aslifitness.fitrackers.todays

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aslifitness.fitrackers.databinding.FragmentTodayWorkoutBinding

/**
 * @author Shubham Pandey
 */
class TodayWorkoutFragment: Fragment() {

    private lateinit var binding: FragmentTodayWorkoutBinding

    companion object {
        const val TAG = "TodayWorkoutFragment"
        fun newInstance() = TodayWorkoutFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTodayWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
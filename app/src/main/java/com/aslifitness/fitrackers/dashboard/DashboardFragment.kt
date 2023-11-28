package com.aslifitness.fitrackers.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aslifitness.fitrackers.databinding.FragmentDashboardBinding
import com.aslifitness.fitrackers.history.HistoryFragment
import com.aslifitness.fitrackers.home.HomeFragment
import com.aslifitness.fitrackers.home.HomePagerAdapter
import com.aslifitness.fitrackers.nutrition.NutritionFragment
import com.aslifitness.fitrackers.routine.UserRoutineFragment
import com.aslifitness.fitrackers.profile.UserProfileFragment
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author Shubham Pandey
 */
class DashboardFragment: Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    companion object {
        const val TAG = "DashboardFragment"
        fun newInstance() = DashboardFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val fragmentList = mutableListOf<Fragment>()
        fragmentList.add(HomeFragment.newInstance())
        fragmentList.add(HistoryFragment.newInstance())
        fragmentList.add(NutritionFragment.newInstance())
        fragmentList.add(UserRoutineFragment.newInstance())
        fragmentList.add(UserProfileFragment.newInstance())
        val homePagerAdapter = HomePagerAdapter(this, fragmentList)
        binding.viewPager.adapter = homePagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = "Object: $position"
        }.attach()
    }
}
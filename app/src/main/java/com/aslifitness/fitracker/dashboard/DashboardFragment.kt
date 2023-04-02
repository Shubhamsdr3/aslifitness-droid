package com.aslifitness.fitracker.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aslifitness.fitracker.databinding.FragmentDashboardBinding
import com.aslifitness.fitracker.history.HistoryFragment
import com.aslifitness.fitracker.home.HomeFragment
import com.aslifitness.fitracker.home.HomePagerAdapter
import com.aslifitness.fitracker.nutrition.NutritionFragment
import com.aslifitness.fitracker.plan.UserPlanFragment
import com.aslifitness.fitracker.profile.UserProfileFragment
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
        fragmentList.add(UserPlanFragment.newInstance())
        fragmentList.add(UserProfileFragment.newInstance())
        val homePagerAdapter = HomePagerAdapter(this, fragmentList)
        binding.viewPager.adapter = homePagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = "Object: $position"
        }.attach()
    }
}
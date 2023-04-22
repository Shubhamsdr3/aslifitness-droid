package com.aslifitness.fitracker.widgets.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.aslifitness.fitracker.databinding.LayoutFitCalendarBinding
import com.aslifitness.fitracker.routine.data.MonthCalendar
import java.util.Calendar

/**
 * Created by shubhampandey
 */
class FitCalendarView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0): ConstraintLayout(context, attributeSet, defStyle) {

    private val binding = LayoutFitCalendarBinding.inflate(LayoutInflater.from(context), this, true)
    private val calender by lazy { Calendar.getInstance() }

    companion object {
        private val ENG_MONTH_NAMES = arrayOf(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
        )
    }

    init {
        initCalendar()
    }

    fun setData(monthlyRoutine: MonthCalendar) {
        configurePager(monthlyRoutine)
        configureScroll()
    }

    private fun initCalendar() {
        configureToday()
    }

    private fun configureScroll() {
        binding.leftArrow.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            binding.viewPager.currentItem = binding.viewPager.currentItem - 1
        }
        binding.rightArrow.setOnClickListener {
            binding.viewPager.currentItem = binding.viewPager.currentItem + 1
        }
    }

    private fun configurePager(monthsRoutine: MonthCalendar) {
        binding.viewPager.adapter = CalendarPageAdapter(monthsRoutine)
        binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                val viewItem = binding.viewPager.getChildAt(position)
//                if (viewItem is CalendarItemView) {
//                    viewItem.setUserCurrentMonthYear(2, 2023)
//                    viewItem.init(context)
//                }
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun configureToday() {
        binding.currentDate.text = calender.get(Calendar.DATE).toString()
        binding.currentMonth.text = ENG_MONTH_NAMES[calender.get(Calendar.MONTH)]
    }
}
package com.aslifitness.fitracker.widgets.calendar

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.aslifitness.fitracker.routine.data.MonthCalendar

/**
 * Created by shubhampandey
 */
class CalendarPageAdapter(private val routine: MonthCalendar) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = CalendarItemView(container.context)
        view.setData(routine)
        container.addView(view, 0)
        return view
    }

    override fun getCount() = Int.MAX_VALUE

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}
package com.aslifitness.fitrackers.widgets.calendar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.ItemCalendarViewBinding
import com.aslifitness.fitrackers.routine.data.DayRoutine
import com.aslifitness.fitrackers.routine.data.MonthCalendar
import com.aslifitness.fitrackers.widgets.calendar.data.CellInfo
import java.util.*


/**
 * Created by shubhampandey
 */

class CalendarItemView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0) : LinearLayout(context, attributeSet, defyStyle) {

    private val binding = ItemCalendarViewBinding.inflate(LayoutInflater.from(context), this, true)

    companion object {
        private const val MAX_WEEK_IN_MONTH = 5
        private const val MAX_DAYS_IN_WEEK = 6
    }

    private val days = ArrayList<CalendarCellView>()
    private val weeks = ArrayList<LinearLayout>(6)
    private var currentDateDay = 0
    private var chosenDateDay = 0
    private var currentDateMonth = 0
    private var chosenDateMonth = 0
    private var currentDateYear = 0
    private var chosenDateYear = 0
    private var pickedDateDay = 0
    private var pickedDateMonth = 0
    private var pickedDateYear = 0
    private var userMonth = 0
    private var userYear = 0
    private var daysLeftInFirstWeek: Int = 0
    private var mListener: DayClickListener? = null
    private val calendar: Calendar by lazy { Calendar.getInstance() }
    private var selectedDayButton: Button? = null
    private var defaultButtonParams: LayoutParams? = null
    private val userButtonParams: LayoutParams? = null
//    private var userRoutines = mutableListOf<CellInfo>()

    fun setData(routineMonth: MonthCalendar) {
        init(context)
        configureRoutines(routineMonth.routines)
    }

    private fun configureRoutines(routines: List<DayRoutine>?) {
        if (routines.isNullOrEmpty()) return
        val dates = days.map { it.getCurrentDate() }
        routines.forEach {
            val currentDate = it.date + daysLeftInFirstWeek - 1
            if (dates.contains(currentDate)) {
                days[currentDate].setLabels(it.labels)
            }
        }
    }

    private fun init(context: Context) {
        chosenDateDay = calendar.get(Calendar.DAY_OF_MONTH)
        currentDateDay = chosenDateDay
        if (userMonth != 0 && userYear != 0) {
            chosenDateMonth = userMonth
            currentDateMonth = chosenDateMonth
            chosenDateYear = userYear
            currentDateYear = chosenDateYear
        } else {
            chosenDateMonth = calendar.get(Calendar.MONTH)
            currentDateMonth = chosenDateMonth
            chosenDateYear = calendar.get(Calendar.YEAR)
            currentDateYear = chosenDateYear
        }
        initializeDaysWeeks()
        defaultButtonParams = userButtonParams ?: getDaysLayoutParams()
        addDaysInCalendar(defaultButtonParams, context)
        initCalendarWithDate(chosenDateYear, chosenDateMonth, chosenDateDay)
    }

    private fun initializeDaysWeeks() {
        weeks.add(binding.calendarWeek1)
        weeks.add(binding.calendarWeek2)
        weeks.add(binding.calendarWeek3)
        weeks.add(binding.calendarWeek4)
        weeks.add(binding.calendarWeek5)
        weeks.add(binding.calendarWeek6)
    }

    private fun initCalendarWithDate(year: Int, month: Int, day: Int) {
        calendar.set(year, month, day)
        val daysInCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        chosenDateYear = year
        chosenDateMonth = month
        chosenDateDay = day
        calendar.set(year, month, 1)
        val firstDayOfCurrentMonth = calendar.get(Calendar.DAY_OF_WEEK)
        calendar[year, month] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        var dayNumber = 1
        val indexOfDayAfterLastDayOfMonth: Int
        // if first day of current month is not sunday
        if (firstDayOfCurrentMonth != 1) {
            daysLeftInFirstWeek = firstDayOfCurrentMonth
            indexOfDayAfterLastDayOfMonth = daysLeftInFirstWeek + daysInCurrentMonth
            for (i in firstDayOfCurrentMonth until firstDayOfCurrentMonth + daysInCurrentMonth) {
                if (currentDateMonth == chosenDateMonth && currentDateYear == chosenDateYear && dayNumber == currentDateDay) {
                    days[i].setSelected()
                    days[i].setTextColor(Color.WHITE)
                } else {
                    days[i].setTextColor(Color.BLACK)
                    days[i].setBackgroundColor(Color.TRANSPARENT)
                }
                configureDay(dayNumber, i)
                ++dayNumber
            }
        } else {
            // if first day of current month is sunday
            daysLeftInFirstWeek = 8
            indexOfDayAfterLastDayOfMonth = daysLeftInFirstWeek + daysInCurrentMonth
            for (i in 8 until 8 + daysInCurrentMonth) {
                if (currentDateMonth == chosenDateMonth && currentDateYear == chosenDateYear && dayNumber == currentDateDay) {
                    days[i].setBackgroundColor(resources.getColor(R.color.error))
                    days[i].setTextColor(Color.WHITE)
                } else {
                    days[i].setTextColor(Color.BLACK)
                    days[i].setBackgroundColor(Color.TRANSPARENT)
                }
                configureDay(dayNumber, i)
                ++dayNumber
            }
        }
        if (month > 0) {
            calendar.set(year, month - 1, 1)
        } else {
            calendar.set(year - 1, 11, 1)
        }
        addPreviousMonthNumbers(daysLeftInFirstWeek)
        addNextMonthNumbers(indexOfDayAfterLastDayOfMonth)
        calendar.set(chosenDateYear, chosenDateMonth, chosenDateDay)
    }

    private fun addNextMonthNumbers(indexOfDayAfterLastDayOfMonth: Int) {
        var nextMonthDaysCounter = 1
        for (i in indexOfDayAfterLastDayOfMonth until days.size) {
            val dateArr = IntArray(3)
            if (chosenDateMonth < 11) {
                if (currentDateMonth == chosenDateMonth + 1 && currentDateYear == chosenDateYear && nextMonthDaysCounter == currentDateDay) {
                    days[i].setBackgroundColor(resources.getColor(R.color.error))
                } else {
                    days[i].setBackgroundColor(Color.TRANSPARENT)
                }
                dateArr[0] = nextMonthDaysCounter
                dateArr[1] = chosenDateMonth + 1
                dateArr[2] = chosenDateYear
            } else {
                if (currentDateMonth == 0 && currentDateYear == chosenDateYear + 1 && nextMonthDaysCounter == currentDateDay) {
                    days[i].setBackgroundColor(resources.getColor(R.color.error))
                } else {
                    days[i].setBackgroundColor(Color.TRANSPARENT)
                }
                dateArr[0] = nextMonthDaysCounter
                dateArr[1] = 0
                dateArr[2] = chosenDateYear + 1
            }
            days[i].tag = dateArr
            days[i].setTextColor(ContextCompat.getColor(context, R.color.divider))
            days[i].setData(CellInfo(nextMonthDaysCounter++, null))
            days[i].setOnClickListener { v -> onDayClick(v) }
        }
    }

    private fun addPreviousMonthNumbers(daysLeftInFirstWeek: Int) {
        var daysInPreviousMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in daysLeftInFirstWeek - 1 downTo 0) {
            val dateArr = IntArray(3)
            if (chosenDateMonth > 0) {
                if (currentDateMonth == chosenDateMonth - 1 && currentDateYear == chosenDateYear && daysInPreviousMonth == currentDateDay) {
                    // do nothing
                } else {
                    days[i].setBackgroundColor(Color.TRANSPARENT)
                }
                dateArr[0] = daysInPreviousMonth
                dateArr[1] = chosenDateMonth - 1
                dateArr[2] = chosenDateYear
            } else {
                if (currentDateMonth == 11 && currentDateYear == chosenDateYear - 1 && daysInPreviousMonth == currentDateDay) {
                } else {
                    days[i].setBackgroundColor(Color.TRANSPARENT)
                }
                dateArr[0] = daysInPreviousMonth
                dateArr[1] = 11
                dateArr[2] = chosenDateYear - 1
            }
            days[i].tag = dateArr
            days[i].setData(CellInfo(daysInPreviousMonth--, null))
            days[i].setOnClickListener { v -> onDayClick(v) }
        }
    }

    private fun configureDay(dayNumber: Int, day: Int) {
        val dateArr = IntArray(3)
        dateArr[0] = dayNumber
        dateArr[1] = chosenDateMonth
        dateArr[2] = chosenDateYear
        days[day].tag = dateArr
        days[day].setData(CellInfo(dayNumber))
        days[day].setOnClickListener { v -> onDayClick(v) }
    }

    private fun onDayClick(view: View?) {
        mListener?.onDayClick(view)
        if (selectedDayButton != null) {
            if (chosenDateYear == currentDateYear && chosenDateMonth == currentDateMonth && pickedDateDay == currentDateDay) {
                selectedDayButton!!.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
                selectedDayButton!!.setTextColor(Color.WHITE)
            } else {
                selectedDayButton!!.setBackgroundColor(Color.TRANSPARENT)
                if (selectedDayButton!!.currentTextColor != Color.RED) {
                    selectedDayButton!!.setTextColor(resources.getColor(R.color.text_dark_tertiary))
                }
            }
        }
        selectedDayButton = view as Button?
        if (selectedDayButton?.tag != null) {
            val dateArray = selectedDayButton?.tag as IntArray
            pickedDateDay = dateArray[0]
            pickedDateMonth = dateArray[1]
            pickedDateYear = dateArray[2]
        }
        if (pickedDateYear == currentDateYear && pickedDateMonth == currentDateMonth && pickedDateDay == currentDateDay) {
            selectedDayButton!!.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
            selectedDayButton!!.setTextColor(Color.WHITE)
        } else {
            selectedDayButton!!.setBackgroundColor(ContextCompat.getColor(context, R.color.divider))
            if (selectedDayButton!!.currentTextColor != Color.RED) {
                selectedDayButton!!.setTextColor(Color.WHITE)
            }
        }
    }

    private fun addDaysInCalendar(cellLayoutParams: LayoutParams?, context: Context) {
        var engDaysArrayCounter = 0
        for (weekNumber in 0..MAX_WEEK_IN_MONTH) {
            for (dayInWeek in 0..MAX_DAYS_IN_WEEK) {
                val calenderCell = CalendarCellView(context).apply {
                    setTextColor(ContextCompat.getColor(context, R.color.divider))
                    layoutParams = cellLayoutParams
                }.also {
                    days.add(engDaysArrayCounter, it)
                    weeks[weekNumber].addView(it)
                    calendar.get(Calendar.WEEK_OF_MONTH)
                }
                ++engDaysArrayCounter
            }
        }
    }

    private fun getDaysLayoutParams(): LayoutParams {
        val buttonParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        buttonParams.weight = 1f
        return buttonParams
    }

    fun setUserCurrentMonthYear(userMonth: Int, userYear: Int) {
        this.userMonth = userMonth
        this.userYear = userYear
    }

    fun setCallBack(mListener: DayClickListener?) {
        this.mListener = mListener
    }

    interface DayClickListener {
        fun onDayClick(view: View?)
    }
}
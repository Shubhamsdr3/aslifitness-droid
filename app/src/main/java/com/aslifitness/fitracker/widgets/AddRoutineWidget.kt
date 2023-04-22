package com.aslifitness.fitracker.widgets

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.LayoutAddRoutineBinding
import com.aslifitness.fitracker.db.UserRoutine
import com.aslifitness.fitracker.model.addworkout.NewAddWorkout
import com.aslifitness.fitracker.utils.parseInt
import com.aslifitness.fitracker.utils.setCircularImage
import com.aslifitness.fitracker.utils.setTextWithVisibility
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by shubhampandey
 */
class AddRoutineWidget @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = LayoutAddRoutineBinding.inflate(LayoutInflater.from(context), this, true)
    private var workout: NewAddWorkout? = null
    private var callback: AddRoutineWidgetCallback? = null
    private val date by lazy { Date() }
    private val calendar by lazy { Calendar.getInstance() }

    companion object {

        private const val HOUR_MNT_PATTERN = "hh:mm aa"
        private const val DASH = "_"

        private val ENG_MONTH_NAMES = arrayOf(
            "Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec"
        )
        private val ENG_DAY_NAMES = arrayOf(
            "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"
        )
    }

    fun setData(workout: NewAddWorkout) {
        this.workout = workout
        workout.run {
            binding.imageWorkout.setCircularImage(image, R.drawable.ic_dumble_new)
            binding.workoutTitle.setTextWithVisibility(title)
            configureRoutines()
            setupListener()
        }
    }

    fun setCallback(callback: AddRoutineWidgetCallback) {
        this.callback = callback
    }

    private fun setupListener() {
        binding.dayContainer.setOnClickListener { openDatePickerDialog() }
        binding.timeContainer.setOnClickListener { openTimePickerDialog() }
        binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            workout?.routineInfo?.reminder?.isRepeat = isChecked
        }
    }

    private fun openTimePickerDialog() {
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val mnt = calendar[Calendar.MINUTE]
        TimePickerDialog(context, { _, hourOfDay, minute -> setTime(hourOfDay, minute) },
            hour,
            mnt,
            false
        ).show()
    }

    private fun setTime(hourOfDay: Int, minute: Int) {
        val sdf = SimpleDateFormat(HOUR_MNT_PATTERN, Locale.getDefault())
        date.hours = hourOfDay
        date.minutes = minute
        binding.timeContainer.setSubtitle(sdf.format(date))
        workout?.routineInfo?.reminder?.time = date
    }

    private fun openDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(context, { _, yr, monthOfYear, dayOfMonth ->
            setDate(calendar, yr, monthOfYear, dayOfMonth) },
            year,
            month,
            day
        ).show()
    }

    private fun setDate(calendar: Calendar, yr: Int, monthOfYear: Int, dayOfMonth: Int) {
        calendar.time = Date(yr, monthOfYear, dayOfMonth)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        binding.dayContainer.setSubtitle(ENG_DAY_NAMES[dayOfWeek] + ", " + dayOfMonth.toString() + " " + ENG_MONTH_NAMES[monthOfYear] + ", " + yr)
        date.year = yr
        date.month = monthOfYear
        date.date = dayOfMonth
    }

    private fun configureRoutines() {
        configureDay()
        configureTime()
        configureSet()
    }

    private fun configureSet() {
        binding.setContainer.setTitle(context.getString(R.string.set_text))
        binding.setContainer.setHintText(DASH)
        binding.setContainer.addTextChangeListener {
            workout?.routineInfo?.workoutSetInfo?.repsCount = it.parseInt()
        }
        binding.setContainer.setInputType(InputType.TYPE_CLASS_NUMBER)
        binding.weightContainer.setTitle(context.getString(R.string.weight_hint))
        binding.weightContainer.setHintText(DASH)
        binding.weightContainer.setInputType(InputType.TYPE_CLASS_NUMBER)
        binding.weightContainer.addTextChangeListener {
            workout?.routineInfo?.workoutSetInfo?.weightInKg = it.parseInt()
        }
    }

    private fun configureTime() {
        binding.timeContainer.setTitle(context.getString(R.string.time_hint))
        binding.timeContainer.setSubtitle(DASH)
    }

    private fun configureDay() {
        binding.dayContainer.setTitle(context.getString(R.string.day_hint))
        binding.dayContainer.setSubtitle(DASH)
    }
}
package com.aslifitness.fitracker.widgets

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.LayoutAddRoutineBinding
import com.aslifitness.fitracker.db.UserRoutine
import com.aslifitness.fitracker.model.Reminder
import com.aslifitness.fitracker.model.addworkout.NewAddWorkout
import com.aslifitness.fitracker.model.addworkout.WorkoutSetInfo
import com.aslifitness.fitracker.routine.data.RoutineInfo
import com.aslifitness.fitracker.routine.data.RoutineWorkout
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
    private var workout: RoutineWorkout? = null
    private var isValidDate = true
    private val scheduledTime by lazy { Calendar.getInstance() }
    private val workoutSetInfo = WorkoutSetInfo()
    private val reminder by lazy { Reminder() }

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

    fun setData(workout: RoutineWorkout) {
        this.workout = workout
        workout.run {
            binding.imageWorkout.setCircularImage(image, R.drawable.ic_dumble_new)
            binding.workoutTitle.setTextWithVisibility(title)
            configureRoutines()
            setupListener()
        }
        workout.routineInfo = RoutineInfo(reminder = reminder, workoutSetInfo = workoutSetInfo)
    }

    private fun setupListener() {
        binding.dayContainer.setOnClickListener { openDatePickerDialog() }
        binding.timeContainer.setOnClickListener { openTimePickerDialog() }
        binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            reminder.isRepeat = isChecked
        }
    }

    private fun openTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val mnt = calendar[Calendar.MINUTE]
        TimePickerDialog(context, { _, hourOfDay, minute -> setTime(hourOfDay, minute) },
            hour,
            mnt,
            false
        ).show()
    }

    private fun setTime(hourOfDay: Int, minute: Int) {
        val currentData = Date()
        if (isValidDate && (hourOfDay >= currentData.hours && minute >= currentData.minutes)) {
            val sdf = SimpleDateFormat(HOUR_MNT_PATTERN, Locale.getDefault())
            currentData.hours = hourOfDay
            currentData.minutes = minute
            binding.timeContainer.setSubtitle(sdf.format(currentData))
            scheduledTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            scheduledTime.set(Calendar.MINUTE, minute)
            reminder.time = scheduledTime.timeInMillis
        } else {
            Toast.makeText(context, "Can't reminder in past.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(context, { _, yr, monthOfYear, dayOfMonth ->
            setDate(yr, monthOfYear, dayOfMonth) },
            year,
            month,
            day
        ).show()
    }

    private fun setDate(yr: Int, monthOfYear: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        isValidDate = isValidDate(calendar, yr, monthOfYear, dayOfMonth)
        if (isValidDate) {
            calendar.set(yr, monthOfYear, dayOfMonth)
            scheduledTime.set(yr, monthOfYear, dayOfMonth)
            reminder.time = scheduledTime.timeInMillis
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            binding.dayContainer.setSubtitle(ENG_DAY_NAMES[dayOfWeek -1] + ", " + dayOfMonth.toString() + " " + ENG_MONTH_NAMES[monthOfYear] + ", " + yr)
        } else {
            Toast.makeText(context, "Can't reminder in past.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidDate(calendar: Calendar, yr: Int, monthOfYear: Int, dayOfMonth: Int): Boolean {
        return if (yr >= calendar.get(Calendar.YEAR)) true
        else if (yr >= calendar.get(Calendar.YEAR) && monthOfYear >= calendar.get(Calendar.MONTH)) true
        else (yr >= calendar.get(Calendar.YEAR) && monthOfYear >= calendar.get(Calendar.MONTH) && dayOfMonth >= calendar.get(Calendar.DATE))
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
            workoutSetInfo.repsCount = it.parseInt()
        }
        binding.setContainer.setInputType(InputType.TYPE_CLASS_NUMBER)
        binding.weightContainer.setTitle(context.getString(R.string.weight_hint))
        binding.weightContainer.setHintText(DASH)
        binding.weightContainer.setInputType(InputType.TYPE_CLASS_NUMBER)
        binding.weightContainer.addTextChangeListener {
            workoutSetInfo.weightInKg = it.parseInt()
        }
    }

    private fun configureTime() {
        binding.timeContainer.setTitle(context.getString(R.string.time_hint))
        val calender = Calendar.getInstance()
        setTime(calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE))
    }

    private fun configureDay() {
        binding.dayContainer.setTitle(context.getString(R.string.day_hint))
        val calender = Calendar.getInstance()
        setDate(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH),  calender.get(Calendar.DATE))
    }
}
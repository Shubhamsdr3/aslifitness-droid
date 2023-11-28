package com.aslifitness.fitrackers.widgets

/**
 * Created by shubhampandey
 */
interface AddRoutineWidgetCallback {

    fun onDaySelected(workoutId: Int, yr: Int, monthOfYear: Int, dayOfMonth: Int)

    fun onTimeSelected(workoutId: Int, hrOfDay: Int, minutes: Int)

    fun onSetCountSelected(workoutId: Int, setCount: Int)

    fun onWeightSelected(workoutId: Int, weightInKg: Int)

    fun onRepeatEnabled(workoutId: Int, isEnabled: Boolean)
}
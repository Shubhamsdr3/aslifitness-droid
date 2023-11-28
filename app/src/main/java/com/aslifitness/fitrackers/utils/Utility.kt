package com.aslifitness.fitrackers.utils

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * @author Shubham Pandey
 */
object Utility {

    private const val DATE_FORMAT = "dd-MM-yyyy"

    private var densityScalingFactor = 0F

    fun getViewWidth(columnSize: Int): Int {
        val singleColumnSize = getSingleColumnSize()
        val columnSizeInPx: Float = when (columnSize) {
            1 -> singleColumnSize
            2 -> 2 * singleColumnSize + 8
            3 -> 3 * singleColumnSize + 16
            4 -> 4 * singleColumnSize + 24
            5 -> 5 * singleColumnSize + 32
            6 -> 6 * singleColumnSize + 40
            else -> 0F
        }
        return (columnSizeInPx * densityScalingFactor).roundToInt()
    }

    private fun getSingleColumnSize(): Float {
        val displayMetrics = Resources.getSystem().displayMetrics
        densityScalingFactor = displayMetrics.density
        var screenWidthDp = displayMetrics.widthPixels / densityScalingFactor
        screenWidthDp -= (MARGINS + FIVE_GUTTERS)
        return screenWidthDp / 6
    }

    fun getCurrentTime(): String {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return sdf.format(Date())
    }

    fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun getDeviceWidth(): Float = Resources.getSystem().displayMetrics.widthPixels.toFloat()

    fun showKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}
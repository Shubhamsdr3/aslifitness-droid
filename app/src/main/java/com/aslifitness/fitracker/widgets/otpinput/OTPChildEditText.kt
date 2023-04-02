package com.aslifitness.fitracker.widgets.otpinput

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat

/**
 * @author Shubham
 */

class OTPChildEditText @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): AppCompatEditText(context, attributeSet, defyStyle) {

    init {
        init(context)
    }

    private fun init(context: Context) {
        isCursorVisible = false
        setTextColor(ResourcesCompat.getColor(context.resources, android.R.color.transparent, null))
        background = null
        inputType = InputType.TYPE_CLASS_NUMBER
        setSelectAllOnFocus(false)
        setTextIsSelectable(false)
    }

    public override fun onSelectionChanged(start: Int, end: Int) {
        val text = text
        text?.let {
            if (start != it.length || end != it.length) {
                setSelection(it.length, it.length)
                return
            }
        }
        super.onSelectionChanged(start, end)
    }

}
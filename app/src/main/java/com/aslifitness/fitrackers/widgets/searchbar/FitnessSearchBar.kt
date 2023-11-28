package com.aslifitness.fitrackers.widgets.searchbar

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.utils.dpToPx

/**
 * @author Shubham Pandey
 */
class FitnessSearchBar @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0) : AppCompatEditText(context, attributeSet, defyStyle) {

    private var callback: FitnessSearchCallback? = null
    private val dimen10Dp = 16.dpToPx

    init {
        setupListener()
        background = ContextCompat.getDrawable(context, R.drawable.rect_grey_border)
        setPadding(dimen10Dp, dimen10Dp, dimen10Dp, dimen10Dp)
        isFocusableInTouchMode = true
    }

    private fun setupListener() {
        addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun afterTextChanged(text: Editable?) {
                text?.let { callback?.onTextChanged(it.toString()) }
            }
        })
    }

    fun setLeftIcon(@DrawableRes resId: Int) {
        setCompoundDrawables(ContextCompat.getDrawable(context, resId), null, null, null)
    }

    fun addOnTextChange(callback: FitnessSearchCallback) {
        this.callback = callback
    }
}
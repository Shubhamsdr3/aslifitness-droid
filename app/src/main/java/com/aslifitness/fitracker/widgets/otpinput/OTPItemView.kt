package com.aslifitness.fitracker.widgets.otpinput

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import com.aslifitness.fitracker.R

/**
 * @author Shubham
 */
class OTPItemView : FrameLayout {

    companion object {
        const val ACTIVE = 1
        const val INACTIVE = 0
        const val ERROR = -1
        const val SUCCESS = 2
    }

    private var textView: AppCompatTextView? = null
    private var view: View? = null
    private var boxBackgroundColorActive: Int = 0
    private var boxBackgroundColorInactive: Int = 0
    private var boxBackgroundColorSuccess: Int = 0
    private var boxBackgroundColorError: Int = 0

    constructor(context: Context) : super(context) {
        generateViews()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        generateViews()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        generateViews()
    }

    private fun generateViews() {
        boxBackgroundColorActive = R.drawable.bg_rect_stroke_primary_info
        boxBackgroundColorInactive = R.drawable.bg_rect_stroke_tertiary_info
        boxBackgroundColorError = R.drawable.bg_rect_stroke_error
        background = ResourcesCompat.getDrawable(context.resources, boxBackgroundColorInactive, null)
        val textViewParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        textViewParams.gravity = Gravity.CENTER
        textView = AppCompatTextView(context)
        textView!!.gravity = Gravity.CENTER
        TextViewCompat.setTextAppearance(textView!!, R.style.TextBody_Regular_Primary)
        addView(textView, textViewParams)
        setViewState(INACTIVE)
    }

    fun setText(value: String) {
        textView?.text = value
    }

    fun setViewState(state: Int) {
        when (state) {
            ACTIVE -> setBackgroundResource(boxBackgroundColorActive)
            ERROR -> setBackgroundResource(boxBackgroundColorError)
            SUCCESS -> setBackgroundResource(boxBackgroundColorSuccess)
            else -> setBackgroundResource(boxBackgroundColorInactive)
        }
    }
}

package com.aslifitness.fitracker.utils

import android.content.res.Resources
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.aslifitness.fitracker.R
import com.bumptech.glide.Glide


/**
 * @author Shubham Pandey
 */
fun AppCompatTextView.setTextWithVisibility(text: CharSequence?) {
    if (!text.isNullOrEmpty()) {
        this.text = text
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun AppCompatTextView.setUnderlineText(text: CharSequence?) {
    if (!text.isNullOrEmpty()) {
        val content = SpannableString(text)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        this.text = content
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun AppCompatImageView.setImageWithVisibility(imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(context).load(imageUrl).into(this)
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun AppCompatImageView.setImageWithPlaceholder(imageUrl: String?, @DrawableRes resId: Int = 0) {
    Glide.with(context).load(imageUrl).placeholder(resId).into(this)
    this.visibility = View.VISIBLE
}

fun AppCompatButton.setButtonTextWithVisibility(text: CharSequence?) {
    if (!text.isNullOrEmpty()) {
        this.text = text
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

val Number.dpToPx get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics
).toInt()


fun View.addDivider(): View {
    val view = View(context)
    view.setBackgroundColor(ContextCompat.getColor(context, R.color.divider))
    val layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, resources.getDimensionPixelSize(
            R.dimen.dimen_1dp))
    view.layoutParams = layoutParams
    return view
}

package com.aslifitness.fitracker.utils

import android.content.res.Resources
import android.text.Editable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.aslifitness.fitracker.R
import com.bumptech.glide.Glide


/**
 * @author Shubham Pandey
 */


fun AppCompatEditText.setTexInEditText(text: CharSequence?) {
    if (!text.isNullOrEmpty()) {
        this.setText(text, TextView.BufferType.EDITABLE)
    }
}

fun AppCompatTextView.setTextWithVisibility(text: CharSequence?) {
    if (!text.isNullOrEmpty()) {
        this.text = text
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun <T> MutableLiveData<List<T>>.notifyObserver(item: T) {
    this.value = this.value
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
        Glide.with(context).load(imageUrl).placeholder(R.drawable.ic_dumble_grey).into(this)
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun AppCompatImageView.setImageWithPlaceHolder(imageUrl: String?, @DrawableRes placeHolder: Int) {
    Glide.with(context).load(imageUrl).placeholder(placeHolder).into(this)
    this.visibility = View.VISIBLE
}

fun AppCompatImageView.setImageWithPlaceholder(imageUrl: String?, @DrawableRes resId: Int = 0) {
    Glide.with(context).load(imageUrl).placeholder(resId).into(this)
    this.visibility = View.VISIBLE
}

fun AppCompatImageView.setCircularImage(imageUrl: String?, @DrawableRes resId: Int = 0) {
    Glide.with(context).load(imageUrl).placeholder(resId).circleCrop().into(this)
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

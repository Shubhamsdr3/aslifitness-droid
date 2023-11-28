package com.aslifitness.fitrackers.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import com.aslifitness.fitrackers.databinding.LayoutTitleEditTextBinding

/**
 * Created by shubhampandey
 */
class TitleEditText @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): LinearLayout(context, attributeSet, defyStyle) {

    private val binding = LayoutTitleEditTextBinding.inflate(LayoutInflater.from(context), this, true)

    fun addTextChangeListener(callback: (CharSequence) -> Unit) {
        binding.value.addTextChangedListener {
            if (!it.isNullOrEmpty()) {
                callback.invoke(it)
            }
        }
    }

    fun setTitle(title: String) {
        binding.key.text = title
    }

    fun  setValue(text: CharSequence) {
        binding.value.setText(text.toString())
    }

    fun setInputType(type: Int) {
        binding.value.inputType = type
    }

    fun setHintText(hint: String) {
        binding.value.hint = hint
    }

    fun getText(): CharSequence? {
        return binding.value.text
    }
}
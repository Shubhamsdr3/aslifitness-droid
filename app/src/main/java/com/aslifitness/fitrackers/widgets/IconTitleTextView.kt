package com.aslifitness.fitrackers.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitrackers.databinding.ItemIconTitleTextViewBinding
import com.aslifitness.fitrackers.utils.gone
import com.aslifitness.fitrackers.utils.setImageWithVisibility
import com.aslifitness.fitrackers.utils.setTextWithVisibility
import com.aslifitness.fitrackers.utils.show

/**
 * Created by shubhampandey
 */
class IconTitleTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = ItemIconTitleTextViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setTitleIcon(imageUrl: String?) {
        binding.titleIcon.setImageWithVisibility(imageUrl)
    }

    fun setTitleIconResource(@DrawableRes resId: Int) {
        binding.titleIcon.setImageResource(resId)
        binding.titleIcon.show()
    }

    fun setTitle(title: String?) {
        binding.tvTitle.setTextWithVisibility(title)
    }
    fun setSubtitle(subTitle: String?) {
        binding.tvSubtitle.setTextWithVisibility(subTitle)
    }
    
    fun setEditable(isEditable: Boolean) {
        if (isEditable) {
            binding.iconEdit.show()
        } else {
            binding.iconEdit.gone()
        }
    }
}
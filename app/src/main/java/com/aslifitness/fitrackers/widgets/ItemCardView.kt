package com.aslifitness.fitrackers.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.ItemCardViewBinding
import com.aslifitness.fitrackers.databinding.SingleItemViewBinding
import com.aslifitness.fitrackers.model.CardItem
import com.aslifitness.fitrackers.model.CtaActionType
import com.aslifitness.fitrackers.utils.setImageWithVisibility
import com.aslifitness.fitrackers.utils.setTextWithVisibility
import com.google.android.material.card.MaterialCardView

/**
 * @author Shubham Pandey
 */
class ItemCardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defyStyle: Int = 0
) : MaterialCardView(context, attributeSet, defyStyle) {

    private val binding = ItemCardViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var callback: ItemCardViewListener? = null
    private var cardItem: CardItem? = null
    private var isDropDownVisible = false

    init {
        cardElevation = ResourcesCompat.getFloat(context.resources, R.dimen.card_elevation)
        strokeWidth = ResourcesCompat.getFloat(context.resources, R.dimen.border_width).toInt()
        radius = context.resources.getDimension(R.dimen.dimen_4dp)
        strokeColor = ContextCompat.getColor(context, R.color.text_dark_secondary)
    }

    fun setData(cardItem: CardItem, callback: ItemCardViewListener) {
        this.callback = callback
        this.cardItem = cardItem
        binding.title.setTextWithVisibility(cardItem.title)
        binding.cardIcon.setImageWithVisibility(cardItem.leftIcon)
        binding.dropdownMenu.setImageWithVisibility(cardItem.rightIcon)
        binding.dropdownMenu.setOnClickListener { onCtaClicked(cardItem.action, cardItem.actionUrl) }
    }

    private fun onCtaClicked(action: String?, actionUrl: String?) {
        when(action) {
            CtaActionType.DROP_DOWN -> configureDropDownItems()
            else -> callback?.onRightIconClicked(action, actionUrl)
        }
    }

    fun setMidIcon(@DrawableRes resId: Int) {
        binding.cardIcon.setImageDrawable(ContextCompat.getDrawable(context, resId))
        binding.cardIcon.visibility = VISIBLE
    }

    fun setLeftIcon(@DrawableRes resId: Int) {
        binding.icLeft.setImageDrawable(ContextCompat.getDrawable(context, resId))
        binding.icLeft.visibility = VISIBLE
    }

    fun setRightIcon(@DrawableRes resId: Int) {
        binding.dropdownMenu.setImageDrawable(ContextCompat.getDrawable(context, resId))
        binding.dropdownMenu.visibility = VISIBLE
    }

    private fun configureDropDownItems() {
        if (!isDropDownVisible && !cardItem?.data.isNullOrEmpty()) {
            val dropDownList = cardItem?.data!!
            for (item in dropDownList) {
                val itemView = SingleItemViewBinding.inflate(LayoutInflater.from(context), null, false)
                itemView.itemKey.setTextWithVisibility(item.header)
                itemView.itemValue.setTextWithVisibility(item.qtyInfo?.setCount.toString())
                binding.itemList.addView(itemView.root)
            }
            binding.itemList.visibility = VISIBLE
            isDropDownVisible = true
        } else {
            binding.itemList.removeAllViews()
            binding.itemList.visibility = GONE
            isDropDownVisible = false
        }
    }
}
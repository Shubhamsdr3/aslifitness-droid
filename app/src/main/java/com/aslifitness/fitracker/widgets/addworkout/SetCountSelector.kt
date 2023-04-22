package com.aslifitness.fitracker.widgets.addworkout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslifitness.fitracker.R
import com.aslifitness.fitracker.databinding.LayoutAddItemButtonBinding
import com.aslifitness.fitracker.model.CtaInfo
import com.aslifitness.fitracker.utils.setCircularImage
import com.aslifitness.fitracker.utils.setTextWithVisibility

/**
 * Created by shubhampandey
 */
class SetCountSelector @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): ConstraintLayout(context, attributeSet, defyStyle) {

    private val binding = LayoutAddItemButtonBinding.inflate(LayoutInflater.from(context), this, true)
    private var callback: SetCountSelectorCallback? = null
    private var setCount: Int = 1

    init {
        initView()
    }

    fun setData(ctaInfo: CtaInfo, callback: SetCountSelectorCallback? = null) {
        this.callback = callback
        binding.itemIcon.setCircularImage(ctaInfo.icon, R.drawable.ic_add_24)
        binding.itemTitle.setTextWithVisibility(ctaInfo.text)
    }

    fun setCallback(callback: SetCountSelectorCallback) {
        this.callback = callback
    }

    private fun initView() {
        binding.addSetContainer.visibility = VISIBLE
        binding.addSetContainer.setOnClickListener { configureQtySelector() }
    }

    private fun configureQtySelector() {
        onPlusClicked()
        binding.addSetContainer.visibility = GONE
        binding.qtySelector.root.visibility = VISIBLE
        binding.qtySelector.countText.text = setCount.toString()
        binding.qtySelector.icPlus.setOnClickListener { onPlusClicked() }
        binding.qtySelector.icMinus.setOnClickListener { onMinusClicked() }
    }

    private fun onMinusClicked() {
        if (setCount > 1) {
            --setCount
            binding.qtySelector.countText.text = setCount.toString()
            callback?.onMinusClicked()
        }
    }

    private fun onPlusClicked() {
        ++setCount
        binding.qtySelector.countText.text = setCount.toString()
        callback?.onPlusClicked()
    }
}
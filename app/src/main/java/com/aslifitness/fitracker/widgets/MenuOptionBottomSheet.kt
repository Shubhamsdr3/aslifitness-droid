package com.aslifitness.fitracker.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.aslifitness.fitracker.databinding.BottomsheetMenuOptionsBinding
import com.aslifitness.fitracker.databinding.ItemMenuOptionBinding
import com.aslifitness.fitracker.model.MenuOption
import com.aslifitness.fitracker.utils.setImageWithVisibility
import com.aslifitness.fitracker.utils.setTextWithVisibility
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by shubhampandey
 */
class MenuOptionBottomSheet: BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetMenuOptionsBinding

    private val menuOptions by lazy {
        arguments?.getParcelableArrayList<MenuOption>(MENU_OPTIONS)
    }

    companion object {
        const val TAG = "MenuOptionBottomSheet"
        private const val MENU_OPTIONS = "menu_options"

        fun newInstance(menuOptions: ArrayList<MenuOption>) = MenuOptionBottomSheet().apply {
            arguments = bundleOf(Pair(MENU_OPTIONS, menuOptions))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomsheetMenuOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureMenuOptions()
    }

    private fun configureMenuOptions() {
        menuOptions?.forEach {
            val itemBinding = ItemMenuOptionBinding.inflate(LayoutInflater.from(context), null, false)
            itemBinding.icon.setImageWithVisibility(it.image)
            itemBinding.title.setTextWithVisibility(it.title)
            binding.menuOptions.addView(itemBinding.root)
        }
    }
}
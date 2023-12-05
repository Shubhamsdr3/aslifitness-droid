package com.aslifitness.fitrackers.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.databinding.BottomSheetChosePictureBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by shubhampandey
 */
class ChosePictureBottomSheet: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetChosePictureBinding

    private var callback: ChosePictureBottomSheetCallback? = null
    companion object {

        const val TAG = "ChosePictureBottomSheet"
        fun newInstance() = ChosePictureBottomSheet()
    }

    override fun getTheme() = R.style.BottomSheetDialogStyle

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment != null && parentFragment is ChosePictureBottomSheetCallback) {
            this.callback = parentFragment as ChosePictureBottomSheetCallback
        } else if (context is ChosePictureBottomSheetCallback) {
            this.callback = context
        } else {
            throw IllegalStateException("$context must implement callback")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomSheetChosePictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener() {
        binding.ivCamera.setOnClickListener {
            callback?.onCameraClicked()
            dismissAllowingStateLoss()
        }
        binding.ivGallery.setOnClickListener {
            callback?.onGalleryClicked()
            dismissAllowingStateLoss()
        }
    }
}
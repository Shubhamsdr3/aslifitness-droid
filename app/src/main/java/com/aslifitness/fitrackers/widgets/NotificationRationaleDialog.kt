package com.aslifitness.fitrackers.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.aslifitness.fitrackers.databinding.DialogNotificationRationaleBinding

/**
 * Created by shubhampandey
 */
class NotificationRationaleDialog: DialogFragment() {

    private lateinit var binding: DialogNotificationRationaleBinding

    companion object {
        const val TAG = "NotificationRationaleDialog"

        fun newInstance() = NotificationRationaleDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogNotificationRationaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
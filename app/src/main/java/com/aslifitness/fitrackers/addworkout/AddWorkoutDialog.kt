package com.aslifitness.fitrackers.addworkout

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.aslifitness.fitrackers.databinding.DialogAddWorkoutBinding
import com.aslifitness.fitrackers.utils.setTextWithVisibility

/**
 * @author Shubham Pandey
 */
class AddWorkoutDialog: DialogFragment() {

    private lateinit var binding: DialogAddWorkoutBinding
    private var callback: AddWorkoutDialogCallback? = null

    companion object {
        const val TAG = "AddWorkoutDialog"
        fun newInstance() = AddWorkoutDialog()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment != null && parentFragment is AddWorkoutDialogCallback) {
            this.callback = parentFragment as AddWorkoutDialogCallback
        } else if (context is AddWorkoutDialogCallback) {
            this.callback = context
        } else {
            throw IllegalStateException("$context must implement fragment context")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogAddWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
    }

    private fun configureViews() {
        binding.header.setTextWithVisibility("Set added !!")
        binding.addWorkoutBtn.text = "Add workout"
        binding.addExerciseBtn.text = "Next exercise"
        binding.icRightTick.playAnimation()
        binding.addWorkoutBtn.setOnClickListener { callback?.onAddWorkoutClicked() }
        binding.addExerciseBtn.setOnClickListener { callback?.onNextExerciseClicked() }
    }

    override fun onResume() {
        val window: Window? = dialog?.window
        val size = Point()
        val display: Display? = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        window?.setLayout((size.x * 0.90).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
        super.onResume()
    }
}
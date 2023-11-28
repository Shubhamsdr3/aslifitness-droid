package com.aslifitness.fitrackers.routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aslifitness.fitrackers.databinding.FragmentUserRoutineBinding
import com.aslifitness.fitrackers.network.ApiHandler
import com.aslifitness.fitrackers.network.ApiResponse
import com.aslifitness.fitrackers.network.NetworkState
import com.aslifitness.fitrackers.routine.data.MonthCalendar
import com.aslifitness.fitrackers.routine.data.UserCalendarResponse
import java.util.*


/**
 * @author Shubham Pandey
 */
class UserRoutineFragment: Fragment() {

    private lateinit var binding: FragmentUserRoutineBinding
    private lateinit var viewModel: UserRoutineViewModel
    private val userId = "gwerbgerwbgerg"
    private var currentMonth = Calendar.getInstance().get(Calendar.MONTH)

    companion object {
        const val TAG = "UserPlanFragment"
        fun newInstance() = UserRoutineFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initViewModel() {
        val factory = UserRoutineViewModelFactory(UserRoutineRepository(ApiHandler.apiService))
        viewModel = ViewModelProvider(this, factory)[UserRoutineViewModel::class.java]
        viewModel.fetchUserRoutine(userId)
        viewModel.userRoutineViewState.observe(viewLifecycleOwner) { onViewStateChanged(it) }
    }

    private fun onViewStateChanged(state: NetworkState<ApiResponse<UserCalendarResponse>>?) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Error -> showError()
            is NetworkState.Success -> onSuccessResponse(state.data)
            else -> {
                // do nothing
            }
        }
    }

    private fun onSuccessResponse(data: ApiResponse<UserCalendarResponse>?) {
        binding.routineLoader.visibility = View.GONE
        data?.data?.run {
//            binding.currentDate.setTextWithVisibility(header)
            configureCalendar(currentMonth)
            configurePageListener(prevMonths)
        }
    }

    private fun configurePageListener(prevMonths: List<MonthCalendar>?) {
        if (prevMonths.isNullOrEmpty()) return
        //FIXME: Shubham
//        binding.calendarView.setOnPreviousPageChangeListener {
//            if (currentMonth-- >= 0 && currentMonth < prevMonths.count()) {
//                configureCalendar(prevMonths[currentMonth])
//            }
//        }
//        binding.calendarView.setOnForwardPageChangeListener {
//            if (currentMonth < prevMonths.count()) {
//                configureCalendar(prevMonths[currentMonth++])
//            }
//        }
    }

    private fun configureCalendar(routine: MonthCalendar?) {
        routine?.let {
            binding.calendarView.setData(it)
            binding.calendarView.visibility = View.VISIBLE
        }
    }

    private fun showError() {
        binding.routineLoader.visibility = View.GONE
    }

    private fun showLoader() {
        binding.routineLoader.visibility = View.VISIBLE
    }

    private fun initView() {
//        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
//            val date = (dayOfMonth.toString() + "-" + (month + 1) + "-" + year)
//            binding.currentDate.text = date
//        }
    }
}
package com.aslifitness.fitrackers.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslifitness.fitrackers.R
import com.aslifitness.fitrackers.auth.UserAuthActivity
import com.aslifitness.fitrackers.databinding.FragmentHomeBinding
import com.aslifitness.fitrackers.db.AppDatabase
import com.aslifitness.fitrackers.home.data.DurationFilterType
import com.aslifitness.fitrackers.model.QuoteInfo
import com.aslifitness.fitrackers.model.UserDto
import com.aslifitness.fitrackers.model.WorkoutDto
import com.aslifitness.fitrackers.model.WorkoutResponse
import com.aslifitness.fitrackers.network.ApiHandler
import com.aslifitness.fitrackers.network.ApiResponse
import com.aslifitness.fitrackers.network.NetworkState
import com.aslifitness.fitrackers.profile.UserProfileActivity
import com.aslifitness.fitrackers.routine.data.UserCalendarResponse
import com.aslifitness.fitrackers.routine.data.UserRoutineDto
import com.aslifitness.fitrackers.sharedprefs.UserStore
import com.aslifitness.fitrackers.summary.SummaryCardViewListener
import com.aslifitness.fitrackers.summary.data.WorkoutSummary
import com.aslifitness.fitrackers.utils.setImageWithPlaceholder
import com.aslifitness.fitrackers.utils.setTextWithVisibility
import com.aslifitness.fitrackers.widgets.QuoteWidgetCallback
import com.aslifitness.fitrackers.workoutlist.WorkoutListActivity
import javax.inject.Inject

/**
 * @author Shubham Pandey
 */
class HomeFragment : Fragment(), QuoteWidgetCallback, SummaryCardViewListener, DurationFilterBottomSheetCallback {

    private lateinit var binding: FragmentHomeBinding

    private var workoutList: List<WorkoutDto>? = null

    @Inject
    lateinit var viewModel: HomeViewModel

    private var currentSelectedDuration: Int = 0

    companion object {
        internal const val TAG = "HomeFragment"

        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        setupListener()
    }

    private fun setupListener() {
        binding.durationFilter.setOnClickListener {
            DurationFilterBottomSheet.newInstance(currentSelectedDuration).show(childFragmentManager, DurationFilterBottomSheet.TAG)
        }
    }

    private fun setupView() {
        binding.bottomText.text = getString(R.string.made_with_love)
        binding.profileImage.setOnClickListener {
            if (UserStore.isUserAuthenticated()) {
                startActivity(Intent(requireActivity(), UserProfileActivity::class.java))
            } else {
                startActivity(Intent(requireActivity(), UserAuthActivity::class.java))
            }
        }
    }

    private fun setupViewModel() {
        val factory = HomeViewModelFactory(HomeRepository(ApiHandler.apiService, AppDatabase.getInstance().fitnessQuoteDao()))
        viewModel = ViewModelProvider(viewModelStore, factory)[HomeViewModel::class.java]
        viewModel.getWorkoutList()
        viewModel.getFitnessQuotes()
        viewModel.fetchRoutineType(DurationFilterType.WEEK.type)
        viewModel.homeNetworkState.observe(viewLifecycleOwner) { onHomeNetworkStateChanged(it) }
        viewModel.homeViewState.observe(viewLifecycleOwner) { onHomeViewStateChanged(it) }
        viewModel.homeRoutineViewState.observe(viewLifecycleOwner) { onHomeRoutineStateChanged(it) }
    }

    private fun onHomeRoutineStateChanged(state: HomeViewState?) {
        when(state) {
            is HomeViewState.ShowUserRoutine -> showUserRoutine(state.userRoutine)
            else -> {
                // do nothing
            }
        }
    }

    private fun showUserRoutine(userRoutine: UserCalendarResponse) {
        userRoutine.currentMonth?.let {
            binding.workoutCalendar.setData(it)
        }
    }

    private fun onHomeViewStateChanged(state: HomeViewState?) {
        when(state) {
            is HomeViewState.ShowFitnessQuotes -> showFitnessQuotes(state.quotes)
            else -> {
                // do nothing
            }
        }
    }

    private fun showFitnessQuotes(quotes: List<QuoteInfo>) {
        if (quotes.isNotEmpty()) {
            binding.quotesList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            binding.quotesList.adapter = FitQuotesAdapter(quotes, this)
            binding.quotesList.visibility = View.VISIBLE
        } else {
            binding.quotesList.visibility = View.GONE
        }
    }

    private fun onHomeNetworkStateChanged(state: NetworkState<ApiResponse<WorkoutResponse>>) {
        when(state) {
            is NetworkState.Loading -> showLoader()
            is NetworkState.Error -> hideLoader()
            is NetworkState.Success -> handleResponse(state.data)
        }
    }

    private fun handleResponse(data: ApiResponse<WorkoutResponse>?) {
        data?.data?.run {
            binding.greeting.setTextWithVisibility(header)
            binding.date.setTextWithVisibility(subHeader)
            configureWorkoutSummary(workoutSummary)
            configureRoutineSummary(routineSummary)
            setupQuoteList(quotes)
            configureUser(userDto)
            configureWorkoutList(items)
        }
    }

    private fun configureRoutineSummary(routineSummary: UserRoutineDto?) {
        binding.workoutRoutine.setOnClickListener {

        }
        routineSummary?.let {
            binding.workoutRoutine.setData(it) {}
        }
    }

    private fun configureWorkoutSummary(workoutSummary: WorkoutSummary?) {
        binding.workoutSummary.setOnClickListener {
            if (!workoutList.isNullOrEmpty()) {
                WorkoutsBottomSheet.newInstance(workoutList!!).show(childFragmentManager, WorkoutsBottomSheet.TAG)
            }
        }
        workoutSummary?.let {
            binding.workoutSummary.setData(it)
            binding.workoutSummary.setSummaryViewListener(this@HomeFragment)

        }
    }

    private fun setupQuoteList(quotes: List<QuoteInfo>?) {
        if (!quotes.isNullOrEmpty()) {
            binding.quotesList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            binding.quotesList.adapter = FitQuotesAdapter(quotes, this)
            binding.quotesList.visibility = View.VISIBLE
        } else {
            binding.quotesList.visibility = View.GONE
        }
    }

    private fun configureUser(userDto: UserDto?) {
        userDto?.run { binding.profileImage.setImageWithPlaceholder(profileImage, R.drawable.ic_dumble_new) }
    }

    private fun configureWorkoutList(workoutList: List<WorkoutDto>?) {
        this.workoutList = workoutList
        hideLoader()
    }

    private fun hideLoader() {
        binding.showLoader = false
        binding.executePendingBindings()
    }

    private fun showLoader() {
        binding.showLoader = true
        binding.executePendingBindings()
    }

    override fun onLikeClicked(isLiked: Boolean, quoteId: Int) {
        viewModel.updateLike(isLiked, quoteId)
    }

    override fun onDurationFilterClicked(selected: Int) {
        this.currentSelectedDuration = selected
        when(selected) {
            DurationFilterType.WEEK.code -> {
                binding.durationFilter.text = getString(R.string.this_week)
            }
            DurationFilterType.MONTH.code -> {
                binding.durationFilter.text = getString(R.string.this_month)
            }
            DurationFilterType.YEAR.code -> {
                binding.durationFilter.text = getString(R.string.this_year)
            }
        }
    }

    override fun onSummaryCardClicked() {

    }

    override fun onAddWorkoutClicked() {
//        if (workoutList.isNullOrEmpty()) return
//        WorkoutsBottomSheet.newInstance(workoutList!!)
    }

    override fun onShareClicked() {
        // do nothing
    }
}
package com.aslifitness.fitrackers.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslifitness.fitrackers.model.QuoteInfo
import com.aslifitness.fitrackers.model.WorkoutResponse
import com.aslifitness.fitrackers.network.ApiResponse
import com.aslifitness.fitrackers.network.NetworkState
import com.aslifitness.fitrackers.sharedprefs.UserStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Shubham Pandey
 */

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel() {

    private val _homeNetworkState: MutableLiveData<NetworkState<ApiResponse<WorkoutResponse>>> = MutableLiveData()
    val homeNetworkState: LiveData<NetworkState<ApiResponse<WorkoutResponse>>> = _homeNetworkState

    private val homeViewMutableState: MutableLiveData<HomeViewState> = MutableLiveData()
    val homeViewState: LiveData<HomeViewState> = homeViewMutableState

    fun getWorkoutList() {
        viewModelScope.launch {
            _homeNetworkState.value = NetworkState.Loading
            repository.fetchWorkoutList(UserStore.getUId())
                .catch { error ->
                    _homeNetworkState.value = NetworkState.Error(error)
                }.collect { response ->
                    _homeNetworkState.value = response
                }
        }
    }

    fun getFitnessQuotes() {
        viewModelScope.launch {
            homeViewMutableState.value = HomeViewState.ShowLoader
            repository.fetchFitnessQuotes()
                .catch {
                    homeViewMutableState.value = HomeViewState.ShowError(it)
                }.collect { response ->
                    handleResponse(response)
                }
        }
    }

    private fun handleResponse(state: NetworkState<ApiResponse<List<QuoteInfo>>>) {
        when(state) {
            is NetworkState.Success -> saveQuote(state.data)
            is NetworkState.Error ->  {}
            is NetworkState.Loading -> {}
        }
    }

    private fun saveQuote(data: ApiResponse<List<QuoteInfo>>?) {
        if (!data?.data.isNullOrEmpty()) {
            val quoteInfo = data?.data?.get(0)
            if (quoteInfo != null && quoteInfo.quote!!.length <= 200) {
                quoteInfo.createdAt = System.currentTimeMillis()
                viewModelScope.launch { repository.saveQuote(quoteInfo) }
            }
        }
        viewModelScope.launch {
            repository.getQuotesFromDB()
                .catch {
                    // do nothing.
                }.collect {
                    homeViewMutableState.value = HomeViewState.ShowFitnessQuotes(it)
                }
        }
    }

    fun getWorkoutListFromFB() {
        viewModelScope.launch {
            _homeNetworkState.value = NetworkState.Loading
            repository.fetchWorkoutListFromFB()
                .catch { error ->
                    _homeNetworkState.value =  NetworkState.Error(error)
                }.collect { response ->
//                    _homeViewState.value = response
                }
        }
    }

    fun showFitnessQuotes() {
        viewModelScope.launch {
            repository.getQuotesFromDB()
                .catch {
                    homeViewMutableState.value = HomeViewState.ShowError(it)
                }.collect {

                }
        }
    }

    fun updateLike(isLiked: Boolean, quoteId: Int) {
        viewModelScope.launch {
            repository.updateLike(isLiked, quoteId)
        }
    }
}
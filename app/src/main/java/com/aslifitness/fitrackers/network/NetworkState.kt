package com.aslifitness.fitrackers.network

/**
 * @author Shubham Pandey
 */

sealed class NetworkState<out T: Any> {

    data class Success<out T: Any>(val data: T? = null): NetworkState<T>()

    data class Error(val throwable: Throwable): NetworkState<Nothing>()

    object Loading: NetworkState<Nothing>()
}
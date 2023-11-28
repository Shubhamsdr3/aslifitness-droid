package com.aslifitness.fitrackers.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

/**
 * @author Shubham Pandey
 */

typealias NetworkAPIInvoke<T> = suspend () -> Response<T>

suspend fun <T : Any> performNetworkCall(
    messageInCaseOfError: String = "Network error",
    allowRetries: Boolean = true,
    numberOfRetries: Int = 2,
    networkApiCall: NetworkAPIInvoke<T>
): Flow<NetworkState<T>> {
    var delayDuration = 1000L
    val delayFactor = 2
    return flow {
        val response = networkApiCall()
        if (response.isSuccessful) {
            response.body()?.let { emit(NetworkState.Success(it)) } ?: emit(NetworkState.Error(IOException("API call successful but empty response body")))
            return@flow
        }
        emit(NetworkState.Error(IOException("API call failed with error - ${response.errorBody()?.string() ?: messageInCaseOfError}")))
        return@flow
    }.catch { e ->
        emit(NetworkState.Error(IOException("Exception during network API call: ${e.message}")))
        return@catch
    }.retryWhen { cause, attempt ->
        if (!allowRetries || attempt > numberOfRetries || cause !is IOException) return@retryWhen false
        delay(delayDuration)
        delayDuration *= delayFactor
        return@retryWhen true
    }.flowOn(Dispatchers.IO)
}

suspend fun <T : Any> makeRequest(dispatcher: CoroutineDispatcher = Dispatchers.Default, onRequest: suspend () -> Response<T>): NetworkState<T> = withContext(dispatcher) {
        safeApiCall {
            with(onRequest()) {
                when {
                    isSuccessful && body() != null -> {
                        NetworkState.Success(body() as T)
                    }
                    else -> {
                        NetworkState.Error(IOException("Network error"))
                    }
                }
            }
        }
    }

private suspend fun <T : Any> safeApiCall(call: suspend () -> NetworkState<T>): NetworkState<T> {
    return try {
        call()
    } catch (e: Exception) {
        NetworkState.Error(IOException("Network error"))
    }
}
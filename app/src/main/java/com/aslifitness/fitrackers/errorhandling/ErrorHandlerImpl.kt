package com.aslifitness.fitrackers.errorhandling

import com.bumptech.glide.load.HttpException
import java.io.IOException
import java.net.HttpURLConnection

/**
 * Created by shubhampandey
 */
class ErrorHandlerImpl: ErrorHandler {

    override fun getError(throwable: Throwable): ErrorEntity {

        return when(throwable) {
            is HttpException -> {
                when(throwable.statusCode) {
                    // no cache found in case of no network, thrown by retrofit -> treated as network error
                    100 -> ErrorEntity.Network

                    // not found
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound

                    // access denied
                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied

                    // unavailable service
                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable

                    // all the others will be treated as unknown error
                    else -> ErrorEntity.Unknown
                }
            }
            is IOException -> ErrorEntity.Network
            else -> ErrorEntity.Unknown
        }
    }

}
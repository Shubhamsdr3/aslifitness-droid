package com.aslifitness.fitrackers.errorhandling

/**
 * Created by shubhampandey
 */
interface ErrorHandler {

    fun getError(throwable: Throwable): ErrorEntity
}
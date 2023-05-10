package com.aslifitness.fitracker.errorhandling

/**
 * Created by shubhampandey
 */
interface ErrorHandler {

    fun getError(throwable: Throwable): ErrorEntity
}
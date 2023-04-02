package com.aslifitness.fitracker.network

/**
 * @author Shubham Pandey
 */
data class ApiResponse<T>(var data: T? = null, var isSuccess: Boolean, var statusCode: Int)

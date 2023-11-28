package com.aslifitness.fitrackers.network

/**
 * @author Shubham Pandey
 */
class ApiHandler {

    companion object {

        @JvmStatic
        val apiService: ApiService by lazy { createService<ApiService>() }

        private inline fun <reified T> createService() = RetrofitHandler.INSTANCE.create(T::class.java)
    }
}
package com.aslifitness.fitracker.network

import com.aslifitness.fitracker.BuildConfig
import com.aslifitness.fitracker.FitApp
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Shubham Pandey
 */
class RetrofitHandler {

    companion object {

        @JvmStatic
        val INSTANCE: Retrofit by lazy {
            val gson: Gson = GsonBuilder()
                .enableComplexMapKeySerialization()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getOkHTTPClient())
                .build()
            retrofit
        }

        private fun getOkHTTPClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            val builder = OkHttpClient.Builder()
            builder.retryOnConnectionFailure(true)
            if (BuildConfig.DEBUG) {
                logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }
                builder.addInterceptor(logging)
                FitApp.getAppContext()?.let {
                    val chuckerInterceptor = ChuckerInterceptor.Builder(it).build()
                    builder.addInterceptor(chuckerInterceptor)
                }
            } else {
                logging.apply { logging.level = HttpLoggingInterceptor.Level.NONE }
            }
            return builder.build()
        }
    }
}
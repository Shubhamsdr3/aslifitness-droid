package com.aslifitness.fitrackers.network

import com.aslifitness.fitrackers.BuildConfig
import com.aslifitness.fitrackers.FitApp
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Shubham Pandey
 */
class RetrofitHandler {

    companion object {

        private const val cacheSize:Long = 10 * 1024 * 1024 // 10MB

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
                .cache(FitApp.getAppContext()?.cacheDir?.let { Cache(it, cacheSize) })
            if (BuildConfig.DEBUG) {
                logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }
                builder.addInterceptor(logging)
                builder.addNetworkInterceptor(Interceptor { chain ->
                    val requestBuilder: Request.Builder = chain.request().newBuilder()
                    requestBuilder.header("Content-Type", "application/json")
                    chain.proceed(requestBuilder.build())
                })
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
package com.aslifitness.fitracker.network

import com.aslifitness.fitracker.BuildConfig
import com.aslifitness.fitracker.FitApp
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Shubham Pandey
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


//    private val certificatePinner = CertificatePinner.Builder()
//        .add(
//            "www.yourdomain.com",
//            "sha256/ZCOF65ADBWPDK8P2V7+mqodtvbsTRR/D74FCU+CEEA="
//        )
//        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okhttp: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okhttp)
            .build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .enableComplexMapKeySerialization()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        val builder = OkHttpClient.Builder()
        builder.retryOnConnectionFailure(true)
//            .certificatePinner(certificatePinner)
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
package com.aslifitness.fitracker.di

import com.aslifitness.fitracker.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by shubhampandey
 */

@Module
@InstallIn(SingletonComponent::class)
class AppDatabaseModule {

    @Provides
    fun provideAppDatabase() = AppDatabase.getInstance()
    @Provides
    fun provideRoutineDao(appDatabase: AppDatabase) = appDatabase.routineDao()
    @Provides
    fun provideFitnessQuoteDao(appDatabase: AppDatabase) = appDatabase.fitnessQuoteDao()
}
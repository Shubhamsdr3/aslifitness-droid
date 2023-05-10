package com.aslifitness.fitracker.routine.di

import com.aslifitness.fitracker.routine.RoutineRepository
import com.aslifitness.fitracker.routine.summary.RoutineSummaryViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Created by shubhampandey
 */


@Module
@InstallIn(ActivityComponent::class)
class RoutineBindingModule {

    @Provides
    fun provideRoutineViewModel(repository: RoutineRepository) = RoutineSummaryViewModel(repository)
}
package com.aslifitness.fitrackers.routine.di

import com.aslifitness.fitrackers.routine.RoutineRepository
import com.aslifitness.fitrackers.routine.summary.RoutineSummaryViewModel
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
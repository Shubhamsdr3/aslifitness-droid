package com.aslifitness.fitrackers.addworkout.di

import com.aslifitness.fitrackers.addworkout.AddWorkoutViewModel
import com.aslifitness.fitrackers.addworkout.WorkoutRepository
import com.aslifitness.fitrackers.network.ApiHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @author Shubham Pandey
 */

@InstallIn(ActivityComponent::class)
@Module
class AddWorkoutModule {

    @Provides
    fun provideAddWorkoutViewModel() = AddWorkoutViewModel(WorkoutRepository(ApiHandler.apiService))
}
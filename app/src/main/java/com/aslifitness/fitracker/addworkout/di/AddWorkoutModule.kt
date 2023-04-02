package com.aslifitness.fitracker.addworkout.di

import com.aslifitness.fitracker.addworkout.AddWorkoutViewModel
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
    fun provideAddWorkoutViewModel() = AddWorkoutViewModel()
}
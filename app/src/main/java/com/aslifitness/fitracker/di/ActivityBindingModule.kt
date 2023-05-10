package com.aslifitness.fitracker.di

import com.aslifitness.fitracker.HomeActivity
import com.aslifitness.fitracker.addworkout.AddWorkoutActivity
import com.aslifitness.fitracker.detail.WorkoutDetailActivity
import com.aslifitness.fitracker.profile.UserProfileActivity
import com.aslifitness.fitracker.stories.ShortVideoActivity
import com.aslifitness.fitracker.workoutlist.WorkoutListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Shubham Pandey
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun provideHomeActivity():HomeActivity

    @ContributesAndroidInjector
    abstract fun provideWorkoutDetailActivity(): WorkoutDetailActivity

    @ContributesAndroidInjector
    abstract fun provideShortVideoActivity(): ShortVideoActivity

    @ContributesAndroidInjector
    abstract fun provideAddWorkoutActivity(): AddWorkoutActivity

    @ContributesAndroidInjector
    abstract fun provideWorkoutListActivity(): WorkoutListActivity

    @ContributesAndroidInjector
    abstract fun provideUserProfileActivity(): UserProfileActivity
}
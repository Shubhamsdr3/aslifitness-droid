package com.aslifitness.fitrackers.di

import com.aslifitness.fitrackers.HomeActivity
import com.aslifitness.fitrackers.addworkout.AddWorkoutActivity
import com.aslifitness.fitrackers.detail.WorkoutDetailActivity
import com.aslifitness.fitrackers.profile.UserProfileActivity
import com.aslifitness.fitrackers.stories.ShortVideoActivity
import com.aslifitness.fitrackers.workoutlist.WorkoutListActivity
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
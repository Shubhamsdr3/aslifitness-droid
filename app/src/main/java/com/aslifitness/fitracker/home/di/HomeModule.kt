package com.aslifitness.fitracker.home.di

import com.aslifitness.fitracker.home.HomeRepository
import com.aslifitness.fitracker.home.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @author Shubham Pandey
 */

@Module
@InstallIn(ActivityComponent::class)
class HomeModule {

    @Provides
    fun provideHomeViewModel(homeRepository: HomeRepository) = HomeViewModel(homeRepository)
}
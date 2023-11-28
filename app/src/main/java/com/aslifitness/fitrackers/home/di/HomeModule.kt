package com.aslifitness.fitrackers.home.di

import com.aslifitness.fitrackers.home.HomeRepository
import com.aslifitness.fitrackers.home.HomeViewModel
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
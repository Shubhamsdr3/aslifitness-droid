package com.aslifitness.fitrackers.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Shubham Pandey
 * provide app level dependency
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideString() = "This is string"
}
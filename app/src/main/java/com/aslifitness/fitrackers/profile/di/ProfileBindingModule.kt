package com.aslifitness.fitrackers.profile.di

import com.aslifitness.fitrackers.profile.UserProfileActivity
import com.aslifitness.fitrackers.profile.UserProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


/**
 * @author Shubham Pandey
 */

@Module
@InstallIn(ActivityComponent::class)
abstract class ProfileBindingModule {

    @ContributesAndroidInjector
    abstract fun provideProfileActivity(): UserProfileActivity

    @ContributesAndroidInjector(modules = [UserProfileModule::class])
    abstract fun provideProfileFragment(): UserProfileFragment
}
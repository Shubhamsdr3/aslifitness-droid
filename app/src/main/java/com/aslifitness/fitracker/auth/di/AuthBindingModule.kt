package com.aslifitness.fitracker.auth.di

import com.aslifitness.fitracker.auth.otpverification.OtpVerificationActivity
import com.aslifitness.fitracker.auth.UserAuthActivity
import com.aslifitness.fitracker.auth.UserLoginFragment
import com.aslifitness.fitracker.auth.otpverification.OtpVerificationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @author Shubham Pandey
 */

@Module
@InstallIn(ActivityComponent::class)
abstract class AuthBindingModule {

    @ContributesAndroidInjector
    abstract fun provideAuthActivity(): UserAuthActivity

    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun provideOtpVerificationFragment(): OtpVerificationFragment

    @ContributesAndroidInjector
    abstract fun provideOtpVerificationActivity(): OtpVerificationActivity

    @ContributesAndroidInjector
    abstract fun provideUserLoginFragment(): UserLoginFragment
}
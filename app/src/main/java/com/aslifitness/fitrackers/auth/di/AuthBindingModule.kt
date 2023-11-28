package com.aslifitness.fitrackers.auth.di

import com.aslifitness.fitrackers.auth.otpverification.OtpVerificationActivity
import com.aslifitness.fitrackers.auth.UserAuthActivity
import com.aslifitness.fitrackers.auth.UserLoginFragment
import com.aslifitness.fitrackers.auth.otpverification.OtpVerificationFragment
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
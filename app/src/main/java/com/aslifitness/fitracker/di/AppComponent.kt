package com.aslifitness.fitracker.di

import android.content.Context
import com.aslifitness.fitracker.FitApp
import com.aslifitness.fitracker.auth.di.AuthBindingModule
import com.aslifitness.fitracker.network.NetworkModule
import com.aslifitness.fitracker.profile.di.ProfileBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * @author Shubham Pandey
 */

@Singleton
@Component(
    modules = [AppModule::class, AuthBindingModule::class, NetworkModule::class, ActivityBindingModule::class, ProfileBindingModule::class, AppDatabaseModule::class]
)
interface AppComponent : AndroidInjector<FitApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
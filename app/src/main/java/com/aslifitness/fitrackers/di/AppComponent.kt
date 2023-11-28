package com.aslifitness.fitrackers.di

import android.content.Context
import com.aslifitness.fitrackers.FitApp
import com.aslifitness.fitrackers.auth.di.AuthBindingModule
import com.aslifitness.fitrackers.network.NetworkModule
import com.aslifitness.fitrackers.profile.di.ProfileBindingModule
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
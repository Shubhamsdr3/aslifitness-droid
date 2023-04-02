package com.aslifitness.fitracker.home.di

import android.content.Context
import com.aslifitness.fitracker.di.ActivityContext
import com.aslifitness.fitracker.home.HomeFragment
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author Shubham Pandey
 */

@Subcomponent(modules = [HomeModule::class])
interface HomeSubComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance @ActivityContext context: Context): HomeSubComponent
    }

    fun inject(fragment: HomeFragment)
}
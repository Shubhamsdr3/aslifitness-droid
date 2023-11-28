package com.aslifitness.fitrackers.home.di

import android.content.Context
import com.aslifitness.fitrackers.di.ActivityContext
import com.aslifitness.fitrackers.home.HomeFragment
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
package com.aslifitness.fitracker.addworkout.di

import android.content.Context
import com.aslifitness.fitracker.addworkout.AddWorkoutFragment
import com.aslifitness.fitracker.di.ActivityContext
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author Shubham Pandey
 */

@Subcomponent(modules = [AddWorkoutModule::class])
interface AddWorkoutComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance @ActivityContext context: Context): AddWorkoutComponent
    }

    fun inject(fragment: AddWorkoutFragment)
}
package com.aslifitness.fitrackers.addworkout.di

import android.content.Context
import com.aslifitness.fitrackers.addworkout.AddWorkoutFragment
import com.aslifitness.fitrackers.di.ActivityContext
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
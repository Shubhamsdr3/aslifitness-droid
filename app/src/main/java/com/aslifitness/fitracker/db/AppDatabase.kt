package com.aslifitness.fitracker.db

import android.content.Context
import androidx.room.*
import com.aslifitness.fitracker.FitApp
import com.aslifitness.fitracker.model.QuoteInfo
import com.aslifitness.fitracker.routine.data.UserRoutineDto
import javax.inject.Singleton

/**
 * Created by shubhampandey
 */

@Database(entities = [UserRoutineDto::class, QuoteInfo::class], version = 1)
@TypeConverters(RoutineConverter::class)
@Singleton
abstract class AppDatabase: RoomDatabase() {

    abstract fun routineDao(): RoutineDao

    abstract fun fitnessQuoteDao(): FitnessQuoteDao

    companion object {

        private const val APP_DB_NAME = "fitness_db"

        private var INSTANCE: AppDatabase? = null

        fun getInstance(): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = FitApp.getAppContext()?.let { buildRoomDB(it) }
                    }
                }
            }
            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                APP_DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}
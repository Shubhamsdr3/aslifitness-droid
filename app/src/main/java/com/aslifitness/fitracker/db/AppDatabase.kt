package com.aslifitness.fitracker.db

import android.content.Context
import androidx.room.*
import com.aslifitness.fitracker.FitApp
import com.aslifitness.fitracker.routine.data.UserRoutineDto

/**
 * Created by shubhampandey
 */

@Database(entities = [UserRoutineDto::class], version = 1)
@TypeConverters(RoutineConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun routineDao(): RoutineDao

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
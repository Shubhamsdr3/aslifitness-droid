package com.aslifitness.fitracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by shubhampandey
 */

@Database(entities = [UserRoutine::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun routineDao(): RoutineDao

    companion object {

        private const val APP_DB_NAME = "fitness_db"

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = buildRoomDB(context)
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
//                .fallbackToDestructiveMigration()
                .build()
    }
}
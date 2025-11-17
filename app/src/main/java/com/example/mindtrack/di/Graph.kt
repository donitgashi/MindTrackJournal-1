package com.example.mindtrack.di

import android.content.Context
import androidx.room.Room
import com.example.mindtrack.data.local.AppDatabase
import com.example.mindtrack.data.repo.EntryRepository

object Graph {
    lateinit var database: AppDatabase
        private set

    val repository: EntryRepository by lazy {
        EntryRepository(database.entryDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "mindtrack.db"
            ).fallbackToDestructiveMigration(false).build()
    }
}
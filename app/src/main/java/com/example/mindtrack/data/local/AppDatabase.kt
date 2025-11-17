package com.example.mindtrack.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Entry::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao
}
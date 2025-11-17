package com.example.mindtrack.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Query("SELECT * FROM entries ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<Entry>>

    @Query("SELECT * FROM entries WHERE id = :id")
    fun observe(id: Long): Flow<Entry?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: Entry): Long

    @Query("DELETE FROM entries WHERE id = :id")
    suspend fun deleteById(id: Long)
}
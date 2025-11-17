package com.example.mindtrack.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val body: String,
    val mood: Int,               // 1..5
    val tagsCsv: String,
    val photoUri: String?,
    val createdAt: Long,
    val updatedAt: Long
)
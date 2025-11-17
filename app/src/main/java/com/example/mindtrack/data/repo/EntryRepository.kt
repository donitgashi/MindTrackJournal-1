package com.example.mindtrack.data.repo

import com.example.mindtrack.data.local.Entry
import com.example.mindtrack.data.local.EntryDao
import kotlinx.coroutines.flow.Flow

class EntryRepository(private val dao: EntryDao) {
    val entries: Flow<List<Entry>> = dao.observeAll()
    fun entry(id: Long): Flow<Entry?> = dao.observe(id)

    suspend fun upsert(e: Entry): Long =
        dao.upsert(e.copy(updatedAt = System.currentTimeMillis()))

    suspend fun delete(id: Long) = dao.deleteById(id)
}
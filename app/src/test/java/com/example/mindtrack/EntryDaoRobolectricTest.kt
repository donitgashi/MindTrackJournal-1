package com.example.mindtrack

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.mindtrack.data.local.AppDatabase
import com.example.mindtrack.data.local.Entry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.room.Room

@RunWith(RobolectricTestRunner::class)
class EntryDaoRobolectricTest {

    private lateinit var db: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun upsert_and_observe() = runBlocking {
        val id = db.entryDao().upsert(
            Entry(title="T", body="B", mood=4, tagsCsv="x,y", photoUri=null, createdAt=1L, updatedAt=1L)
        )
        val got = db.entryDao().observe(id).first()
        assertEquals("T", got?.title)
        assertEquals(4, got?.mood)
    }
}
package com.example.mindtrack.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mindtrack.data.local.Entry
import com.example.mindtrack.data.repo.EntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEditEntryViewModel(private val repo: EntryRepository) : ViewModel() {

    var editingId: Long? = null
    var title: String = ""
    var body: String = ""
    var mood: Int = 3
    var tagsCsv: String = ""
    var photoUri: String? = null

    fun load(entry: Entry) {
        editingId = entry.id
        title = entry.title
        body = entry.body
        mood = entry.mood
        tagsCsv = entry.tagsCsv
        photoUri = entry.photoUri
    }

    fun save(onDone: (Long) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        val id = repo.upsert(
            Entry(
                id = editingId ?: 0L,
                title = title,
                body = body,
                mood = mood,
                tagsCsv = tagsCsv,
                photoUri = photoUri,
                createdAt = editingId?.let { now } ?: now,
                updatedAt = now
            )
        )
        onDone(id)
    }
}

class AddEditVMFactory(private val repo: EntryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditEntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return AddEditEntryViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }
}
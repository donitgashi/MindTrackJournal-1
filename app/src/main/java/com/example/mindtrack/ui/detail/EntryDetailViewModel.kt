package com.example.mindtrack.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mindtrack.data.local.Entry
import com.example.mindtrack.data.repo.EntryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn

class EntryDetailViewModel(private val repo: EntryRepository, id: Long) : ViewModel() {
    val entry: StateFlow<Entry> = repo.entry(id).filterNotNull().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000),
        Entry(0,"","",3,"",null,0L,0L)
    )
}

class EntryDetailVMFactory(private val repo: EntryRepository, private val id: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntryDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return EntryDetailViewModel(repo, id) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }
}
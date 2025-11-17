package com.example.mindtrack.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mindtrack.data.local.Entry
import com.example.mindtrack.data.repo.EntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EntryListViewModel(private val repo: EntryRepository) : ViewModel() {

    private val query = MutableStateFlow("")

    private val all: StateFlow<List<Entry>> = repo.entries.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val entries: StateFlow<List<Entry>> = combine(all, query) { list, q ->
        if (q.isBlank()) list else list.filter {
            it.title.contains(q, true) ||
            it.body.contains(q, true) ||
            it.tagsCsv.contains(q, true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setQuery(q: String) { query.value = q }

    fun delete(id: Long) = viewModelScope.launch(Dispatchers.IO) { repo.delete(id) }
}

class EntryListVMFactory(private val repo: EntryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntryListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return EntryListViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }
}
package com.aarav.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import com.aarav.notesapp.repository.CategoryRepository
import com.aarav.notesapp.repository.NotesRepository
import com.aarav.notesapp.roomdb.Category
import com.aarav.notesapp.roomdb.Note
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NotesRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val allNotes: StateFlow<List<Note>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCategories: StateFlow<List<Category>> = categoryRepository.allCategories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedCategoryId = MutableStateFlow<Int?>(null)
    val selectedCategoryId: StateFlow<Int?> = _selectedCategoryId

    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredNotes: StateFlow<List<Note>> = _selectedCategoryId.flatMapLatest { categoryId ->
        when (categoryId) {
            null -> repository.allNotes
            -1 -> repository.getUncategorizedNotes()
            else -> repository.getNotesByCategory(categoryId)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSelectedCategory(categoryId: Int?) {
        _selectedCategoryId.value = categoryId
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun findNote(noteID: Int): Flow<Note?> {
        return repository.findNote(noteID)
    }

    fun updateNote(noteID: Int, title: String, description: String, color: Int, categoryId: Int?, isPinned: Boolean) {
        viewModelScope.launch {
            repository.updateNote(noteID, title, description, color, categoryId, isPinned)
        }
    }

    fun updateNotePinStatus(noteID: Int, isPinned: Boolean) {
        viewModelScope.launch {
            repository.updateNotePinStatus(noteID, isPinned)
        }
    }

    private val searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val notes: StateFlow<List<Note>> = searchQuery.flatMapLatest { query ->
        repository.searchNotes(query)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun insertCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.insertCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
        }
    }

    fun updateCategory(categoryId: Int, name: String, color: Int) {
        viewModelScope.launch {
            categoryRepository.updateCategory(categoryId, name, color)
        }
    }

    suspend fun getBackupData(): Pair<List<Note>, List<Category>> {
        return Pair(repository.getAllNotesSync(), categoryRepository.getAllCategoriesSync())
    }

    fun restoreBackup(notes: List<Note>, categories: List<Category>) {
        viewModelScope.launch {
            // Get existing to prevent exact duplicates
            val existingNotes = repository.getAllNotesSync()
            val existingTitles = existingNotes.map { it.title }.toSet()
            
            // Insert categories first
            if (categories.isNotEmpty()) {
                categoryRepository.insertCategories(categories)
            }
            
            // Filter duplicates by title for notes and set ID to 0 for auto-generate
            val newNotes = notes
                .filter { it.title !in existingTitles }
                .map { it.copy(id = 0) } 

            if (newNotes.isNotEmpty()) {
                repository.insertNotes(newNotes)
            }
        }
    }
}
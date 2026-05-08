package com.aarav.notesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.aarav.notesapp.repository.CategoryRepository
import com.aarav.notesapp.repository.NotesRepository
import com.aarav.notesapp.roomdb.Category
import com.aarav.notesapp.roomdb.Note
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NotesRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val allNotes: LiveData<List<Note>> = repository.allNotes
    val allCategories: LiveData<List<Category>> = categoryRepository.allCategories

    private val _selectedCategoryId = MutableLiveData<Int?>(null)
    val selectedCategoryId: LiveData<Int?> = _selectedCategoryId

    val filteredNotes: LiveData<List<Note>> = _selectedCategoryId.switchMap { categoryId ->
        when (categoryId) {
            null -> repository.allNotes
            -1 -> repository.getUncategorizedNotes()
            else -> repository.getNotesByCategory(categoryId)
        }
    }

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

    fun findNote(noteID: Int): LiveData<Note> {
        return repository.findNote(noteID)
    }

    fun updateNote(noteID: Int, title: String, description: String, color: Int, categoryId: Int?) {
        viewModelScope.launch {
            repository.updateNote(noteID, title, description, color, categoryId)
        }
    }

    private val searchQuery = MutableLiveData("")

    val notes = searchQuery.switchMap { query ->
        repository.searchNotes(query)
    }

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
}
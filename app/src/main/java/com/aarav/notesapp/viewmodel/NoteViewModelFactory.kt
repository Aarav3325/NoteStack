package com.aarav.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aarav.notesapp.repository.CategoryRepository
import com.aarav.notesapp.repository.NotesRepository

class NoteViewModelFactory(
    private val repository: NotesRepository,
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository, categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
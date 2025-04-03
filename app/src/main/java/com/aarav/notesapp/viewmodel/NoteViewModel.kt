package com.aarav.notesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarav.notesapp.repository.NotesRepository
import com.aarav.notesapp.roomdb.Note
import com.aarav.notesapp.roomdb.NotesDB


import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NotesRepository) : ViewModel() {

    val allNotes : LiveData<List<Note>> = repository.allNotes


    // Launching a new coroutine to insert the data in a non-blocking way
    // viewModelScope is a coroutine scope tied to the ViewModel's lifecycle
    // It will be automatically canceled when the ViewModel is cleared
    // launch is a coroutine builder that creates a new coroutine and
    // returns a reference to the coroutine as a Job object
    fun insertNote(note : Note){
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun deleteNote(note : Note){
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun findNote(noteID : Int) : LiveData<Note>{
        return repository.findNote(noteID)
    }

    fun updateNote(noteID : Int, title : String, description : String, color : Int){
        viewModelScope.launch {
            repository.updateNote(noteID, title, description, color)
        }
    }

    private val searchQuery = MutableLiveData("")

    private val _notes = MediatorLiveData<List<Note>>()
    val notes = searchQuery.switchMap { query ->
        repository.searchNotes(query)
    }


//    init {
//        _notes.addSource(searchQuery) { query ->
//            viewModelScope.launch {
//                repository.searchNotes(query).observeForever { searchResults ->
//                    _notes.value = searchResults
//                }
//            }
//        }
//    }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }




}
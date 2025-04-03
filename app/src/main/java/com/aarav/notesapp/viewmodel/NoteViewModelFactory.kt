package com.aarav.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.aarav.notesapp.repository.NotesRepository

class NoteViewModelFactory(val repository: NotesRepository) : ViewModelProvider.Factory{
    // override the create method of ViewModelProvider.Factory
    /*A ViewModel Factory in Android is used to create instances of a ViewModel,
    especially when it requires dependencies such as repositories or use cases.
    The default ViewModelProvider can only instantiate ViewModels with a no-argument constructor,
    so a ViewModelProvider.Factory is needed for custom ViewModel creation.*/
    // This method is called when the ViewModel is created
    // It takes a Class object as an argument and returns a ViewModel object

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
//modelClass is the class type of the ViewModel requested.
//NoteViewModel::class.java represents the NoteViewModel class.
// isAssignableFrom() checks if modelClass is the same as or a subclass of NoteViewModel
// A new instance of NoteViewModel is created, passing the repository dependency.
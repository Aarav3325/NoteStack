package com.aarav.notesapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.aarav.notesapp.roomdb.Note
import com.aarav.notesapp.roomdb.NoteDao

class NotesRepository(private val noteDAO : NoteDao) {
    val allNotes : LiveData<List<Note>> = noteDAO.getNotes()

    suspend fun insertNote(note : Note){
        return noteDAO.insertNote(note)
    }

    suspend fun deleteNote(note : Note){
        return noteDAO.deleteNote(note)
    }

    fun findNote(noteID : Int) : LiveData<Note>{
        val findNote : LiveData<Note> = noteDAO.findNote(noteID)
        return findNote
    }

    suspend fun updateNote(noteID : Int, title : String, description : String, color : Int){
        return noteDAO.updateNote(noteID, title, description, color)
    }

    fun searchNotes(query: String): LiveData<List<Note>> {
        return noteDAO.searchNotes("%$query%") // Ensure partial search works
    }
}
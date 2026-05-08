package com.aarav.notesapp.repository

import androidx.lifecycle.LiveData
import com.aarav.notesapp.roomdb.Note
import com.aarav.notesapp.roomdb.NoteDao

class NotesRepository(private val noteDAO: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDAO.getNotes()

    suspend fun insertNote(note: Note) {
        noteDAO.insertNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDAO.deleteNote(note)
    }

    fun findNote(noteID: Int): LiveData<Note> {
        return noteDAO.findNote(noteID)
    }

    suspend fun updateNote(noteID: Int, title: String, description: String, color: Int, categoryId: Int?, isPinned: Boolean) {
        noteDAO.updateNote(noteID, title, description, color, categoryId, isPinned)
    }

    suspend fun updateNotePinStatus(noteID: Int, isPinned: Boolean) {
        noteDAO.updateNotePinStatus(noteID, isPinned)
    }

    fun searchNotes(query: String): LiveData<List<Note>> {
        return noteDAO.searchNotes("%$query%")
    }

    fun getNotesByCategory(categoryId: Int): LiveData<List<Note>> {
        return noteDAO.getNotesByCategory(categoryId)
    }

    fun getUncategorizedNotes(): LiveData<List<Note>> {
        return noteDAO.getUncategorizedNotes()
    }
}
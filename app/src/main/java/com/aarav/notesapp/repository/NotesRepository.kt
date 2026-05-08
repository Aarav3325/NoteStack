package com.aarav.notesapp.repository

import kotlinx.coroutines.flow.Flow
import com.aarav.notesapp.roomdb.Note
import com.aarav.notesapp.roomdb.NoteDao

class NotesRepository(private val noteDAO: NoteDao) {

    val allNotes: Flow<List<Note>> = noteDAO.getNotes()

    suspend fun insertNote(note: Note) {
        noteDAO.insertNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDAO.deleteNote(note)
    }

    fun findNote(noteID: Int): Flow<Note?> {
        return noteDAO.findNote(noteID)
    }

    suspend fun updateNote(noteID: Int, title: String, description: String, color: Int, categoryId: Int?, isPinned: Boolean) {
        noteDAO.updateNote(noteID, title, description, color, categoryId, isPinned)
    }

    suspend fun updateNotePinStatus(noteID: Int, isPinned: Boolean) {
        noteDAO.updateNotePinStatus(noteID, isPinned)
    }

    fun searchNotes(query: String): Flow<List<Note>> {
        return noteDAO.searchNotes("%$query%")
    }

    fun getNotesByCategory(categoryId: Int): Flow<List<Note>> {
        return noteDAO.getNotesByCategory(categoryId)
    }

    fun getUncategorizedNotes(): Flow<List<Note>> {
        return noteDAO.getUncategorizedNotes()
    }

    suspend fun getAllNotesSync(): List<Note> = noteDAO.getAllNotesSync()

    suspend fun insertNotes(notes: List<Note>) = noteDAO.insertNotes(notes)
}
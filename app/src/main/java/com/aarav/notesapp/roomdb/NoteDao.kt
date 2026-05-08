package com.aarav.notesapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes_table")
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id = :noteID")
    fun findNote(noteID: Int): LiveData<Note>

    @Query("UPDATE notes_table SET title = :title, description = :description, color = :color, category_id = :categoryId WHERE id = :noteID")
    suspend fun updateNote(noteID: Int, title: String, description: String, color: Int, categoryId: Int?)

    @Query("SELECT * FROM notes_table WHERE title LIKE :query OR description LIKE :query")
    fun searchNotes(query: String): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE category_id = :categoryId")
    fun getNotesByCategory(categoryId: Int): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE category_id IS NULL")
    fun getUncategorizedNotes(): LiveData<List<Note>>
}
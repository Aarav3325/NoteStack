package com.aarav.notesapp.roomdb

import kotlinx.coroutines.flow.Flow
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

    @Query("SELECT * FROM notes_table ORDER BY is_pinned DESC, id DESC")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id = :noteID")
    fun findNote(noteID: Int): Flow<Note?>

    @Query("UPDATE notes_table SET title = :title, description = :description, color = :color, category_id = :categoryId, is_pinned = :isPinned WHERE id = :noteID")
    suspend fun updateNote(noteID: Int, title: String, description: String, color: Int, categoryId: Int?, isPinned: Boolean)

    @Query("UPDATE notes_table SET is_pinned = :isPinned WHERE id = :noteID")
    suspend fun updateNotePinStatus(noteID: Int, isPinned: Boolean)

    @Query("SELECT * FROM notes_table WHERE title LIKE :query OR description LIKE :query ORDER BY is_pinned DESC, id DESC")
    fun searchNotes(query: String): Flow<List<Note>>

    @Query("SELECT * FROM notes_table WHERE category_id = :categoryId ORDER BY is_pinned DESC, id DESC")
    fun getNotesByCategory(categoryId: Int): Flow<List<Note>>

    @Query("SELECT * FROM notes_table WHERE category_id IS NULL ORDER BY is_pinned DESC, id DESC")
    fun getUncategorizedNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes_table")
    suspend fun getAllNotesSync(): List<Note>

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<Note>)
}
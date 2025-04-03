package com.aarav.notesapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    // Define methods for various DB operations

    // suspend in Kotlin are used in co-routines to perform asynchronous operations w/o blocking a thread
    // suspend keyword masks a function as a suspendable, meaning it can pause its execution and resume later
    // allowing for a non blocking code and non-blocking
    /* non blocking means the operations that doesn't halt while waiting for a task to complete
    * allowing the UI to remain responsive
    * includes long running task such as network request and DB operations
    * coroutine must be called within a coroutine or another suspend function
    * */
    @Insert
    suspend fun insertNote(note : Note)


    @Delete
    suspend fun deleteNote(note : Note)

    @Query("SELECT * FROM notes_table")
    fun getNotes() : LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id = :noteID")
    fun findNote(noteID : Int) : LiveData<Note>

    @Query("UPDATE notes_table SET title = :title, description = :description, color = :color WHERE id = :noteID")
    suspend fun updateNote(noteID : Int, title : String, description : String, color : Int)

    @Query("SELECT * FROM notes_table WHERE title LIKE :query OR description LIKE :query")
    fun searchNotes(query: String): LiveData<List<Note>>

}
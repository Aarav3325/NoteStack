package com.aarav.notesapp.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NotesDB : RoomDatabase() {

    abstract val notesDAO : NoteDao

    // volatile prevents any race condition in multi threading
    //companion object : define a static singleton instance of the DB class
    companion object{

        @Volatile
        private var INSTANCE : NotesDB? = null

        fun getInstance(context: Context) : NotesDB{
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null){
                   instance = Room.databaseBuilder(
                        context = context,
                        NotesDB::class.java,
                        "motes_db"
                    ).build()
                }

                INSTANCE = instance
                return instance
            }
        }

    }

}
package com.aarav.notesapp.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo(name = "title")
    val title : String,
    @ColumnInfo(name = "description")
    val description : String,

    @ColumnInfo(name = "color")
    val color : Int // Color is stored as ARGB Integer

    // Room doesn't directly supports complex types like Color
)
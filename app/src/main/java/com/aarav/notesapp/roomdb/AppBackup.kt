package com.aarav.notesapp.roomdb

import androidx.annotation.Keep

@Keep
data class AppBackup(
    val notes: List<Note> = emptyList(),
    val categories: List<Category> = emptyList()
)

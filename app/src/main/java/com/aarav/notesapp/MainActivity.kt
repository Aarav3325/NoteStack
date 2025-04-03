package com.aarav.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.aarav.notesapp.navigation.NavGraph
import com.aarav.notesapp.repository.NotesRepository
import com.aarav.notesapp.roomdb.Note
import com.aarav.notesapp.roomdb.NotesDB
import com.aarav.notesapp.screens.DisplayDialog
import com.aarav.notesapp.screens.DisplayNotesList
import com.aarav.notesapp.ui.theme.NotesAppTheme
import com.aarav.notesapp.viewmodel.NoteViewModel
import com.aarav.notesapp.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = NotesDB.getInstance(applicationContext)

        val repository = NotesRepository(database.notesDAO)

        val viewModelFactory = NoteViewModelFactory(repository)

        val noteViewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]

//        val note1 = Note(0, "This is a demo note", "This is a demo description", "#f59597".toColorInt())
//
//        noteViewModel.insetNote(note1)

//        val note1 = Note(1, "Grocery List", "Buy milk, eggs, bread, and fruits.", "#f59597".toColorInt())
//        val note2 = Note(2, "Meeting Notes", "Discuss project updates and deadlines.", "#f38588".toColorInt())
//        val note3 = Note(3, "Workout Plan", "Morning run, strength training, and yoga.", "#8db8e3".toColorInt())
//        val note4 = Note(4, "Book Recommendations", "Read 'Atomic Habits' and 'Deep Work'.", "#c09cc8".toColorInt())
//        val note5 = Note(5, "Travel Checklist", "Passport, tickets, hotel booking, essentials.", "#9999cd".toColorInt())
//        val note6 = Note(6, "Project Ideas", "Build a habit tracker app with reminders.", "#9fd5be".toColorInt())
//        val note7 = Note(7, "Expense Tracker", "Record daily expenses and budget planning.", "#dfe581".toColorInt())
//        val note8 = Note(8, "Coding Goals", "Practice DSA, Android development, and system design.", "#e2eb92".toColorInt())
//
//        noteViewModel.insertNote(note1)
//        noteViewModel.insertNote(note2)
//        noteViewModel.insertNote(note3)
//        noteViewModel.insertNote(note4)
//        noteViewModel.insertNote(note5)
//        noteViewModel.insertNote(note6)
//        noteViewModel.insertNote(note7)
//        noteViewModel.insertNote(note8)




        // observeAsState is a composable function that converts a LiveData object to a State object
        // that can be observed withing a composable function
        setContent {
            NotesAppTheme {

                val navController = rememberNavController()

                val notes by noteViewModel.allNotes.observeAsState(emptyList())
                Scaffold(
                    floatingActionButton =
                        {
                            MyFab(viewModel = noteViewModel)
                        },
                    content = { paddingValues ->
                        NavGraph(
                            navController,
                            noteViewModel,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                )

            }
        }
    }

    @Composable
    fun MyFab(viewModel: NoteViewModel) {

        var showDialog by remember {
            mutableStateOf(false)
        }

        DisplayDialog(viewModel, showDialog) {
            showDialog = false
        }

        FloatingActionButton(
            onClick = {
                showDialog = true
            },
            containerColor = Color.Blue, // Color("0xFF8db8e3")
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add icon"
            )
        }
    }
}



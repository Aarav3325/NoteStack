package com.aarav.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.aarav.notesapp.navigation.NavGraph
import com.aarav.notesapp.repository.NotesRepository
import com.aarav.notesapp.roomdb.NotesDB
import com.aarav.notesapp.ui.theme.NotesAppTheme
import com.aarav.notesapp.viewmodel.NoteViewModel
import com.aarav.notesapp.viewmodel.NoteViewModelFactory
import com.aarav.notesapp.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = NotesDB.getInstance(applicationContext)
        val notesRepository = NotesRepository(database.notesDAO)
        val categoryRepository = com.aarav.notesapp.repository.CategoryRepository(database.categoryDAO)
        val viewModelFactory = NoteViewModelFactory(notesRepository, categoryRepository)
        val noteViewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]

        val themeViewModel = ViewModelProvider(this)[ThemeViewModel::class.java]

        setContent {
            val themeMode by themeViewModel.themeMode.collectAsState()

            NotesAppTheme(themeMode = themeMode) {
                val navController = rememberNavController()

                NavGraph(
                    navController = navController,
                    viewModel = noteViewModel,
                    themeViewModel = themeViewModel
                )
            }
        }
    }
}

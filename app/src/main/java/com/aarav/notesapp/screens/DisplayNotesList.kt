package com.aarav.notesapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aarav.notesapp.roomdb.Note
import com.aarav.notesapp.viewmodel.NoteViewModel

@Composable
fun DisplayNotesList(navController: NavController, viewModel: NoteViewModel) {

    val notes by viewModel.allNotes.observeAsState(emptyList())
    val notesSearch by viewModel.notes.observeAsState(emptyList())
    var searchText by remember { mutableStateOf("") }

//    if(notesSearch.isEmpty()){
//        Text("No Notes Found")
//    }

    Column(modifier = Modifier.padding(top = 45.dp)) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                viewModel.setSearchQuery(it) // Update LiveData in ViewModel
            },
            colors = TextFieldDefaults.colors(),
            placeholder = { Text("Search notes...") },
            modifier = Modifier.fillMaxWidth().padding( start = 16.dp, end = 16.dp).clip(RoundedCornerShape(20.dp)),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            singleLine = true,
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { searchText = ""; viewModel.setSearchQuery("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear Search")
                    }
                }
            }
        )



        if (searchText.isEmpty()) {
            // LazyVerticalStaggeredGrid is a composable that displays a list of items in a staggered grid
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 15.dp, start = 12.dp, end = 12.dp)
            ) {
                items(notes) { note ->
                    NoteListItem(note = note, viewModel) {
                        navController.navigate("update/${note.id}")
                    }
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                items(notesSearch) { note ->
                    NoteListItem(note = note, viewModel) {
                        navController.navigate("update/${note.id}")
                    }
                }
            }
        }
    }

}
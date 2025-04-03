package com.aarav.notesapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.aarav.notesapp.roomdb.Note
import com.aarav.notesapp.viewmodel.NoteViewModel

@Composable
fun UpdateNoteScreen(navigateToHome: () -> Unit, viewModel: NoteViewModel, noteID: Int) {
    val note by viewModel.findNote(noteID).observeAsState()

    // States for title, description, and stored color
    var title by remember { mutableStateOf(note?.title ?: "") }
    var description by remember { mutableStateOf(note?.description ?: "") }
    var color by remember { mutableIntStateOf(note?.color ?: Color.Blue.toArgb()) } // Original note color

    // Store the selected color separately
    var selectedColor by remember { mutableIntStateOf(color) } // Start with note color but separate

    //Log.i("MYTAG", "Stored Color: ${String.format("#%08X", color)} | Selected Color: ${String.format("#%08X", selectedColor)}")

    LaunchedEffect(note) {
        note?.let {
            title = it.title
            description = it.description
            color = it.color // Store original color
            selectedColor = it.color // Initialize selected color
        }
    }

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        border = CardDefaults.outlinedCardBorder(true),
        colors = CardDefaults.cardColors(containerColor = Color(color)),
        modifier = Modifier.fillMaxWidth().padding(top = 60.dp, start = 8.dp, end = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = "Title")
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black
                ),
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Description")
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black
                ),
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Pass selectedColor, but don't update background when changing color
            Card(
                shape = RoundedCornerShape(15.dp),
            ){
                    MyColorPicker(selectedColor = Color(color), onColorSelected = { color = it.toArgb() })
                }


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (note != null) {
                        viewModel.updateNote(noteID, title, description, color)
                        navigateToHome()
                    }
                }
            ) {
                Text(text = "Update Note")
            }
        }
    }
}

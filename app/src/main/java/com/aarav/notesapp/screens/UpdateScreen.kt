package com.aarav.notesapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.aarav.notesapp.viewmodel.NoteViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateNoteScreen(navigateToHome: () -> Unit, viewModel: NoteViewModel, noteID: Int) {
    val note by viewModel.findNote(noteID).observeAsState()

    var title by remember { mutableStateOf(note?.title ?: "") }
    var description by remember { mutableStateOf(note?.description ?: "") }
    var color by remember { mutableIntStateOf(note?.color ?: NoteColors[0].toArgb()) }
    var categoryId by remember { mutableStateOf<Int?>(null) }
    
    var showAddCategoryDialog by remember { mutableStateOf(false) }

    val categories by viewModel.allCategories.observeAsState(emptyList())

    LaunchedEffect(note) {
        note?.let {
            title = it.title
            description = it.description
            color = it.color
            categoryId = it.categoryId
        }
    }

    val noteColor = Color(color)
    val isLight = noteColor.luminance() > 0.5f
    val contentColor = if (isLight) Color(0xFF1C1B1F) else Color.White
    val borderColor = contentColor.copy(alpha = 0.2f)
    val focusedBorderColor = contentColor.copy(alpha = 0.45f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Note",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateToHome) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                colors = CardDefaults.cardColors(containerColor = noteColor),
                border = BorderStroke(0.5.dp, contentColor.copy(alpha = 0.1f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.labelLarge,
                        color = contentColor.copy(alpha = 0.65f),
                        modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
                    )

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.titleMedium,
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = focusedBorderColor,
                            unfocusedBorderColor = borderColor,
                            focusedContainerColor = contentColor.copy(alpha = 0.04f),
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = contentColor,
                            focusedTextColor = contentColor,
                            unfocusedTextColor = contentColor,
                            focusedPlaceholderColor = contentColor.copy(alpha = 0.35f),
                            unfocusedPlaceholderColor = contentColor.copy(alpha = 0.35f)
                        ),
                        placeholder = {
                            Text("Enter title", style = MaterialTheme.typography.titleMedium)
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider(
                        color = contentColor.copy(alpha = 0.08f),
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.labelLarge,
                        color = contentColor.copy(alpha = 0.65f),
                        modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp),
                        textStyle = MaterialTheme.typography.bodyLarge,
                        maxLines = 14,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = focusedBorderColor,
                            unfocusedBorderColor = borderColor,
                            focusedContainerColor = contentColor.copy(alpha = 0.04f),
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = contentColor,
                            focusedTextColor = contentColor,
                            unfocusedTextColor = contentColor,
                            focusedPlaceholderColor = contentColor.copy(alpha = 0.35f),
                            unfocusedPlaceholderColor = contentColor.copy(alpha = 0.35f)
                        ),
                        placeholder = {
                            Text("Write something...", style = MaterialTheme.typography.bodyLarge)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 14.dp)) {
                    Text(
                        text = "Category",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                    )

                    CategorySelector(
                        categories = categories,
                        selectedCategoryId = categoryId,
                        onCategorySelected = { categoryId = it },
                        onAddCategory = { showAddCategoryDialog = true }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Note Color",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                    )

                    MyColorPicker(
                        selectedColor = Color(color),
                        onColorSelected = { color = it.toArgb() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    if (note != null) {
                        viewModel.updateNote(noteID, title, description, color, categoryId)
                        navigateToHome()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Save Changes",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    AddCategoryDialog(
        showDialog = showAddCategoryDialog,
        onDismiss = { showAddCategoryDialog = false },
        viewModel = viewModel
    )
}

package com.aarav.notesapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aarav.notesapp.R
import com.aarav.notesapp.roomdb.Note
import com.aarav.notesapp.viewmodel.NoteViewModel


@Composable
fun NoteListItem(note: Note, viewModel: NoteViewModel, onClick: () -> Unit) {

    var showDelete by remember {
        mutableStateOf(false)
    }


    Card(
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(note.color)),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, top = 6.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        showDelete = true
                    },
                    onTap = {onClick()}
                )
            }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = note.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = note.description,
                fontSize = 16.sp
            )

        }
//            AnimatedVisibility(visible = showDelete, enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.expandVertically(),
//                exit = androidx.compose.animation.fadeOut() + androidx.compose.animation.shrinkVertically()) {
//                var vis = newDisplayDelete(note, viewModel, showDelete)
//                showDelete = vis
//            }

        AnimatedVisibility(
            visible = showDelete,
//            enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.expandVertically(),
//            exit = androidx.compose.animation.fadeOut() + androidx.compose.animation.shrinkVertically()
        ) {
            newDisplayDelete(note, viewModel) { showDelete = false }
        }
    }


//            if(showDelete){
//                DisplayDelete(showDelete = showDelete)
//            }

}


@Composable
fun newDisplayDelete(note: Note, viewModel: NoteViewModel, onClose: () -> Unit) {
    var vis by remember {
        mutableStateOf(true)
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red)
            .border(width = 1.dp, color = Color.Black)
            .padding(top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    viewModel.deleteNote(note)
                    onClose()
                },
            painter = painterResource(id = R.drawable.delete),
            contentDescription = "Delete Icon",
            tint = Color.White
        )

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onClose()
                },
            painter = painterResource(id = R.drawable.cross),
            contentDescription = "Cross Icon",
            tint = Color.White
        )
    }
}
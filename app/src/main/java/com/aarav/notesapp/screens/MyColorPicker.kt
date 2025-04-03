package com.aarav.notesapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import java.nio.file.WatchEvent

@Composable
fun MyColorPicker(selectedColor: Color, onColorSelected : (Color) -> Unit){
    val colorsList = listOf(
        Color("#f59597".toColorInt()),
        Color("#f38588".toColorInt()),
        Color("#8db8e3".toColorInt()),
        Color("#c09cc8".toColorInt()),
        Color("#9999cd".toColorInt()),
        Color("#9fd5be".toColorInt()),
        Color("#dfe581".toColorInt()),
        Color("#e2eb92".toColorInt()),
        Color("#faa385".toColorInt())
    )

    LazyRow(horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(8.dp)) {

        items(colorsList){
            color->

            Box(modifier = Modifier
                .size(40.dp)
                .padding(4.dp)
                .clip(CircleShape)
                .background(color = color)
                .border(
                    width = if (color == selectedColor) 2.dp else 0.dp,
                    color = if (color == selectedColor) Color.Black else Color.Transparent,
                    shape = CircleShape
                )
                .clickable { onColorSelected(color) }
            )
        }
    }
}
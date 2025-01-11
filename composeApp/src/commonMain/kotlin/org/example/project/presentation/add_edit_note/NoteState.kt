package org.example.project.presentation.add_edit_note

import androidx.compose.ui.graphics.Color

data class NoteState(
    val titleText: String = "",
    val contentText: String = "",
    val color: Color = Color(0xFFBB86FC),
    val textColor: Color = Color(0xFFBB86FC),
    val desColor: Color = Color(0xFFBB86FC),
    val deleteColor: Color = Color(0xFFBB86FC),
)
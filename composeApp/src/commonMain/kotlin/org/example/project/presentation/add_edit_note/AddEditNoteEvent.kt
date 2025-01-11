package org.example.project.presentation.add_edit_note

import androidx.compose.ui.graphics.Color
import org.example.project.data.Note

sealed class AddEditNoteEvent {
    data class EnteredTitle(val value: String) : AddEditNoteEvent()
    data class EnteredContent(val value: String) : AddEditNoteEvent()
    data class ChangeColor(val note: Note.ColorModel) : AddEditNoteEvent()
    object SaveNote : AddEditNoteEvent()
}
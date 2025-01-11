package org.example.project.presentation.notes

import org.example.project.data.Note
import org.example.project.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    data object RestoreNote : NotesEvent()
    data object ToggleOrderSection : NotesEvent()
    data class SearchItem(val searchText: String) : NotesEvent()
}
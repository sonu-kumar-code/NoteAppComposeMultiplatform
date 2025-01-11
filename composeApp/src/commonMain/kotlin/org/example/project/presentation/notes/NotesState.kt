package org.example.project.presentation.notes

import org.example.project.data.Note
import org.example.project.domain.util.NoteOrder
import org.example.project.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val searchText: String = ""
)
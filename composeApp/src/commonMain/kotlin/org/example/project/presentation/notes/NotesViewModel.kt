package org.example.project.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.example.project.data.Note
import org.example.project.domain.NoteUseCases
import org.example.project.domain.util.NoteOrder
import org.example.project.domain.util.OrderType

class NotesViewModel(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state: StateFlow<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class && state.value.noteOrder.orderType == event.noteOrder.orderType) {
                    return
                }
                getNotes(event.noteOrder)
            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }

            is NotesEvent.SearchItem -> {
                _state.value = state.value.copy(
                    searchText = event.searchText
                )
                getNotes(state.value.noteOrder, event.searchText)
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder, searchText: String = "") {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder, searchText).onEach { notes ->
            _state.value = state.value.copy(
                notes = notes, noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }
}
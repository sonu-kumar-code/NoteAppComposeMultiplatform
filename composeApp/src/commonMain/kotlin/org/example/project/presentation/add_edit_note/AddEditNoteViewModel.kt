package org.example.project.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.project.Route
import org.example.project.data.InvalidNoteException
import org.example.project.data.Note
import org.example.project.domain.NoteUseCases

class AddEditNoteViewModel(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteState = MutableStateFlow(NoteState("", ""))
    val noteState: StateFlow<NoteState> = _noteState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.toRoute<Route.AddNoteScreenRoute>().let { noteId ->
            noteId.noteId?.let {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId.noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteState.value = noteState.value.copy(
                            titleText = note.title,
                            contentText = note.content,
                            color = note.color
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteState.value = noteState.value.copy(
                    titleText = event.value
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteState.value = noteState.value.copy(
                    contentText = event.value
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteState.value = noteState.value.copy(
                    color = event.note.backgroundColor,
                    textColor = event.note.titleColor,
                    desColor = event.note.contentColor,
                    deleteColor = event.note.deleteIconColor
                )
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = noteState.value.titleText,
                                content = noteState.value.contentText,
                                timestamp = Clock.System.now().epochSeconds,
                                color = noteState.value.color,
                                textColor = noteState.value.textColor,
                                desColor = noteState.value.desColor,
                                deleteColor = noteState.value.deleteColor,
                                id = currentNoteId ?: 0
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SaveNote : UiEvent()
    }
}
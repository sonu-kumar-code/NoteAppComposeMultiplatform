package org.example.project.domain

import org.example.project.data.InvalidNoteException
import org.example.project.data.Note
import kotlin.coroutines.cancellation.CancellationException

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class, CancellationException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}

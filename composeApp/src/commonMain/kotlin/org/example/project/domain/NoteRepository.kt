package org.example.project.domain

import kotlinx.coroutines.flow.Flow
import org.example.project.data.Note

interface NoteRepository {

    fun getNotes(title: String): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}
package org.example.project.data

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.NoteRepository

class NoteRepositoryImpl(private val dao: NoteDao) : NoteRepository {

    override fun getNotes(title: String): Flow<List<Note>> {
        return dao.getAllNote(title)
    }

    override suspend fun getNoteById(id: Int): Note {
        return dao.getNote(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.noteInsert(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.noteDelete(note)
    }
}
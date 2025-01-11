package org.example.project

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object NoteScreenRoute

    @Serializable
    data class AddNoteScreenRoute(
        val noteId: Int?
    )
}
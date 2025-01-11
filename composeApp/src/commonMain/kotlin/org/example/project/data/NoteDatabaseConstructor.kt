package org.example.project.data

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object NoteDatabaseConstructor: RoomDatabaseConstructor<NoteDatabase> {
    override fun initialize(): NoteDatabase
}
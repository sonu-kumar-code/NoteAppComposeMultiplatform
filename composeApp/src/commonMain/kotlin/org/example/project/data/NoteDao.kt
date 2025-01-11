package org.example.project.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun noteInsert(note: Note)

    @Delete
    suspend fun noteDelete(note: Note)

    @Query("SELECT * FROM note where title like '%' || :title || '%'")
    fun getAllNote(title: String): Flow<List<Note>>

    @Query("SELECT * FROM note where id=:id")
    suspend fun getNote(id: Int): Note

}
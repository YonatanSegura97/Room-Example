package com.room.example.app.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.room.example.app.model.Note


@Dao
interface NoteDao {

    @Query("SELECT * from note_table ORDER BY title ASC")
    fun getAlphabetizedNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteElement(note: Note)
}
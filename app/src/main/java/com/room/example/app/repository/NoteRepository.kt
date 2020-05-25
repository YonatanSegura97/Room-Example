package com.room.example.app.repository


import androidx.lifecycle.LiveData
import com.room.example.app.local.NoteDao
import com.room.example.app.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes:LiveData<List<Note>> = noteDao.getAlphabetizedNotes()

    suspend fun insertNote(note: Note){
        noteDao.insert(note)
    }

    suspend fun deleteElement(note: Note){
        noteDao.deleteElement(note)
    }


}
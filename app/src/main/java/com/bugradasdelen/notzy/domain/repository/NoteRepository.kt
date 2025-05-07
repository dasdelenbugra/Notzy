package com.bugradasdelen.notzy.domain.repository

import androidx.lifecycle.LiveData
import com.bugradasdelen.notzy.data.source.local.dao.NoteDao
import com.bugradasdelen.notzy.domain.model.Note
import java.util.Date

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    fun getNoteById(id: Int): LiveData<Note> {
        return noteDao.getNoteById(id)
    }

    suspend fun insert(note: Note): Long {
        return noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        note.updatedAt = Date()
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
}
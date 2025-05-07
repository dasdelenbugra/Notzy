package com.bugradasdelen.notzy.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var content: String,
    val createdAt: Date = Date(),
    var updatedAt: Date = Date()
)
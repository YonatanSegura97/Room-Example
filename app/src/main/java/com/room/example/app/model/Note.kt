package com.room.example.app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note_table")
data class Note(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "date", defaultValue = "CURRENT_TIMESTAMP")
    val date: String

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}
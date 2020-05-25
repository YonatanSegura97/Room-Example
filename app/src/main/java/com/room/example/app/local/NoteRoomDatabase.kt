package com.room.example.app.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.room.example.app.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1, exportSchema = false)
public abstract class NoteRoomDatabase : RoomDatabase() {


    abstract fun noteDao(): NoteDao


    class NoteDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {


        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val noteDao = database.noteDao()

                    noteDao.deleteAll()


                }
            }
        }

    }

    companion object {

        @Volatile
        private var INSTANCE: NoteRoomDatabase? = null

        fun getDatabase(context: Context): NoteRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteRoomDatabase::class.java,
                    "note_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
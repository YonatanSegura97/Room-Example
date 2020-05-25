package com.room.example.app.di

import android.app.Application
import androidx.room.Room
import com.room.example.app.local.NoteDao
import com.room.example.app.local.NoteRoomDatabase
import com.room.example.app.repository.NoteRepository
import com.room.example.app.viewmodel.NoteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { NoteViewModel(get()) }
}

val repositoryModule = module {
    factory { NoteRepository(get()) }
}


val roomModule = module {
    fun provideDao(database: NoteRoomDatabase): NoteDao {
        return database.noteDao()
    }
    single { NoteRoomDatabase.getDatabase(androidContext()) }
    single { provideDao(get()) }
}
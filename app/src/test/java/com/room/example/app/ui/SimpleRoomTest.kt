package com.room.example.app.ui

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import com.room.example.app.local.NoteDao
import com.room.example.app.local.NoteRoomDatabase
import com.room.example.app.model.Note
import com.room.example.app.repository.NoteRepository
import com.room.example.app.utils.getValueBlocking
import com.room.example.app.viewmodel.NoteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.util.concurrent.Executors

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@LooperMode(LooperMode.Mode.PAUSED)
class SimpleRoomTest : KoinTest {

    @Mock
    //private val noteViewModel: NoteViewModel by inject()
    //private val repo: NoteRepository by inject()
    //private val testDao: NoteDao by inject()
    lateinit var db: NoteRoomDatabase
    lateinit var noteViewModel: NoteViewModel
    lateinit var noteDao: NoteDao
    lateinit var repository: NoteRepository
    lateinit var allNotesObserver: Observer<List<Note>>
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        val app = RuntimeEnvironment.application
        db = Room.inMemoryDatabaseBuilder(
            app, NoteRoomDatabase::class.java
        ).setTransactionExecutor(Executors.newSingleThreadExecutor()).build()
        noteDao = db.noteDao()
        repository = NoteRepository(noteDao)
        noteViewModel = NoteViewModel(repository)
        MockitoAnnotations.initMocks(this)
        allNotesObserver = mock(Observer::class.java) as Observer<List<Note>>
        Dispatchers.setMain(testDispatcher)

    }


    @After
    fun cleanUp() {
        db.close()
        stopKoin()
    }

    @Test
    fun `get empty list if table is empty`() {
        val resultLiveData = noteDao.getAlphabetizedNotes()
        val noteObject = resultLiveData.getValueBlocking()
        assertTrue(noteObject.isNullOrEmpty())

    }

    @Test
    fun `save record and verify if live data observe`() {
        val note = listOf(
            Note("testTitle1", "descriptiontest", "12/12/12"),
            Note("testTitle2", "descriptiontest", "12/12/12"),
            Note("testTitle3", "descriptiontest", "12/12/12"),
            Note("testTitle4", "descriptiontest", "12/12/12")
        )
        runBlocking {
            note.forEach {
                noteDao.insert(it)
            }
        }
        assertTrue(noteViewModel.allNotes.getValueBlocking()?.size!! == 4)
    }

    @Test
    fun `when delete all elements verify if live data observe`() {
        val notes = listOf(
            Note("testTitle1", "descriptiontest", "12/12/12"),
            Note("testTitle2", "descriptiontest", "12/12/12"),
            Note("testTitle3", "descriptiontest", "12/12/12"),
            Note("testTitle4", "descriptiontest", "12/12/12")
        )
        runBlocking {
            notes.forEach {
                noteDao.insert(it)
            }
            noteDao.deleteAll()
        }
        assertTrue(noteViewModel.allNotes.getValueBlocking()?.isEmpty()!!)

    }

    @Test
    fun `when delete element verify`() {
        val notes = listOf(
            Note("testTitle1", "descriptiontest", "12/12/12"),
            Note("testTitle2", "descriptiontest", "12/12/12"),
            Note("testTitle3", "descriptiontest", "12/12/12"),
            Note("testTitle4", "descriptiontest", "12/12/12")
        )
        runBlocking {
            notes.forEach {
                noteDao.insert(it)
            }
            noteDao.deleteElement(notes[0])
        }
        assertTrue(noteViewModel.allNotes.getValueBlocking()?.contains(notes[0])!!)
    }

    @Test
    fun `sum`() {
        assertEquals(2, 1 + 1)
    }
}
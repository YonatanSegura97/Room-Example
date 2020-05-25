package com.room.example.app.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.room.example.app.R
import com.room.example.app.databinding.FragmentListBinding
import com.room.example.app.model.Note
import com.room.example.app.viewmodel.NoteViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(),NoteAdapter.OnNoteClick {

    private val sharedViewModel: NoteViewModel by sharedViewModel()
    lateinit var binding: FragmentListBinding
    lateinit var noteAdapter: NoteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureAdapter()
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        sharedViewModel.allNotes.observe(viewLifecycleOwner, Observer { notes ->
            if (notes.isNotEmpty()) Log.d("Insert","Note: ${notes[0].date}")
            noteAdapter.setNotes(notes)
        })


    }

    private fun configureAdapter() {
        noteAdapter = NoteAdapter(this)
        binding.rvNotes.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = noteAdapter
        }
    }

    override fun onDeleteClickListener(position: Int, note: Note) {
        noteAdapter.notifyItemRemoved(position)
        sharedViewModel.deleteElement(note)
    }
}

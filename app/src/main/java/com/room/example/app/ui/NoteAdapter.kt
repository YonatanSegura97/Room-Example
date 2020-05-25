package com.room.example.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.room.example.app.databinding.ItemLayoutBinding
import com.room.example.app.model.Note

class NoteAdapter(private val onNoteClick: OnNoteClick) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notes = emptyList<Note>()


    // OnCLick
    interface OnNoteClick {
        fun onDeleteClickListener(position: Int, note: Note)
    }

    class NoteViewHolder(private val itemLayoutBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemLayoutBinding.root) {
        fun bind(note: Note, onNoteClick: OnNoteClick, position: Int) {
            itemLayoutBinding.imageView.setOnClickListener {
                onNoteClick.onDeleteClickListener(position, note)
            }
            itemLayoutBinding.txtDescription.text = note.description
            itemLayoutBinding.txtTitle.text = note.title
            itemLayoutBinding.txtDate.text = note.date
        }
    }

    internal fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position], onNoteClick, position)
    }
}
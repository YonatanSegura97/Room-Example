package com.room.example.app.ui

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.room.example.app.R
import com.room.example.app.databinding.FragmentInsertBinding
import com.room.example.app.model.Note
import com.room.example.app.viewmodel.NoteViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    lateinit var binding: FragmentInsertBinding
    private val sharedViewModel: NoteViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInsertBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bntInsert.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val description = binding.txtDescription.text.toString()
            val date = binding.txtDate.text.toString()
            insertNote(title, description, date)

            binding.txtTitle.setText("")
            binding.txtDescription.setText("")
            binding.txtDate.setText("")

        }

        binding.btnChooseDate.setOnClickListener {
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH) + 1
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, _, dayOfMonth ->
                    val date = "$dayOfMonth/$month/$year"
                    binding.txtDate.setText(date)
                },
                mYear,
                month,
                day
            )

            dpd.show()
        }

    }

    private fun insertNote(title: String, description: String, date: String) {
        if (title.isEmpty() || description.isEmpty() || date.isEmpty()) {
            return
        }

        val note = Note(description = description, title = title, date = date)
        sharedViewModel.insertNote(note)

        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)

    }
}

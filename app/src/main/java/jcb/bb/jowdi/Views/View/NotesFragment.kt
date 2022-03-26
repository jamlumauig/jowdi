package jcb.bb.jowdi.Views.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jcb.bb.jowdi.databinding.FragmentNotesBinding
import androidx.test.core.app.ApplicationProvider.getApplicationContext

import android.content.SharedPreferences
import android.app.AlertDialog
import android.content.Context
import android.widget.ListView
import android.widget.ArrayAdapter

import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import jcb.bb.jowdi.R

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    lateinit var listView: ListView

    var notes = ArrayList<String>()
    var arrayAdapter: ArrayAdapter<String>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        AddNote()
        initialize()

        return binding.root
    }

    private fun initialize() {
        listView = binding.listView
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("jcb.bb.jowdi", Context.MODE_PRIVATE)

        val set = sharedPreferences.getStringSet("notes", null) as HashSet<String>?

        if (set == null) {
            notes.add("Example Note")
        } else {
            notes =
                ArrayList(set) // to bring all the already stored data in the set to the notes ArrayList
        }

        arrayAdapter = ArrayAdapter(requireContext(), R.layout.fragment_notes, notes)
        listView.adapter = arrayAdapter

        listView.onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                binding.secondLayout.visibility = View.VISIBLE
                binding.firstLayout.visibility = View.GONE

            }

        listView.onItemLongClickListener =
            OnItemLongClickListener { parent, view, position, id ->
                AlertDialog.Builder(context) // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                    .setIcon(R.drawable.ic_baseline_stop_24)
                    .setTitle("Delete?")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes"
                    ) { _, _ ->
                        // to remove the selected note once "Yes" is pressed
                        notes.removeAt(position)
                        arrayAdapter!!.notifyDataSetChanged()
                        val sharedPreferences =
                            getApplicationContext<Context>().getSharedPreferences(
                                "jcb.bb.jowdi",
                                Context.MODE_PRIVATE
                            )
                        sharedPreferences.edit().putStringSet("notes", set).apply()
                    }
                    .setNegativeButton("No", null)
                    .show()
                true // this was initially false but we change it to true as if false, the method assumes that we want to do a short click after the long click as well
            }
    }

    private fun AddNote() {
        binding.addNote.setOnClickListener {
            binding.secondLayout.visibility = View.VISIBLE
            binding.firstLayout.visibility = View.GONE
        }
    }


}
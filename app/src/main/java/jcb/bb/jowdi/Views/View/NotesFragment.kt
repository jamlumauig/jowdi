package jcb.bb.jowdi.Views.View

import android.annotation.SuppressLint
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

import android.widget.AdapterView.OnItemLongClickListener

import android.text.Editable

import android.text.TextWatcher
import jcb.bb.jowdi.Adapter.AdapterOnClick
import jcb.bb.jowdi.Adapter.NotesAdapter

class NotesFragment : Fragment(), AdapterOnClick {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    lateinit var listView: ListView

    lateinit var arrayAdapter: NotesAdapter
    var notes = ArrayList<String>()

    var noteID: Int = 0

    var set: String = String()
    var idName: Int = 0


    val sharedPreferences = getApplicationContext<Context>().getSharedPreferences(
        "jcb.bb.jowdi",
        Context.MODE_PRIVATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        //initialize()
        listView = binding.listView

        firstactivity()
        retrieve()
        add()
        return binding.root
    }

    fun firstactivity() {

        binding.addNote.setOnClickListener {
            binding.firstLayout.visibility = View.GONE
            binding.secondLayout.visibility = View.VISIBLE
        }
        dialog()

    }

    fun dialog() {
        set = (sharedPreferences.getStringSet("notes", null) as HashSet<String>?).toString()

      //  notes = value.
        for (value in set) {
            arrayAdapter = NotesAdapter(notes, this)
            listView.adapter = arrayAdapter
        }
        // Using custom listView Provided by Android Studio

        // Using custom listView Provided by Android Studio
        /*arrayAdapter = NotesAdapter(notes, this)

        listView.adapter = arrayAdapter*/

        /*  listView.onItemLongClickListener =
              OnItemLongClickListener { _, _, i, _ ->
                  val itemToDelete = i
                  // To delete the data from the App
                  AlertDialog.Builder(requireContext())
                      .setIcon(android.R.drawable.ic_dialog_alert)
                      .setTitle("Are you sure?")
                      .setMessage("Do you want to delete this note?")
                      .setPositiveButton(
                          "Yes"
                      ) { _, i ->
                          notes.removeAt(itemToDelete)
                          arrayAdapter!!.notifyDataSetChanged()
                          val sharedPreferences =
                              getApplicationContext<Context>()
                                  .getSharedPreferences(
                                      "com.example.notes",
                                      Context.MODE_PRIVATE
                                  )
                          val set: HashSet<String> = HashSet()
                          sharedPreferences.edit().putStringSet("notes", set).apply()
                      }.setNegativeButton("No", null).show()
                  true
              }*/
    }

    fun add() {
        val editor: SharedPreferences.Editor =
            requireActivity().getSharedPreferences("jcb.bb.jowdi", Context.MODE_PRIVATE).edit()
        editor.putString("name", "jowdi")
        editor.putInt("ID", 1)
        editor.apply()
    }

    fun retrieve() {
        set = sharedPreferences.getString("name", "No name defined").toString()

        idName = sharedPreferences.getInt("idName", 0)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initialize() {

        if (noteID != -1) {
            binding.editText.setText(notes[noteID])
        } else {
            notes.add("")
            noteID = notes.size - 1
            arrayAdapter.notifyDataSetChanged()
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // add your code here
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                notes[noteID] = charSequence.toString()
                arrayAdapter.notifyDataSetChanged()
                val sharedPreferences = getApplicationContext<Context>().getSharedPreferences(
                    "jowdi",
                    Context.MODE_PRIVATE
                )
                val set: HashSet<String> = HashSet(notes)
                sharedPreferences.edit().putStringSet("notes", set).apply()
            }

            override fun afterTextChanged(editable: Editable) {
                // add your code here
            }
        })

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onAdapterClick(positon: Int) {
        binding.firstLayout.visibility = View.GONE
        binding.secondLayout.visibility = View.VISIBLE
       //initialize()

        noteID = positon
        if (noteID != -1) {
            binding.editText.setText(notes[noteID])
        } else {
            notes.add("")
            noteID = notes.size - 1
            arrayAdapter.notifyDataSetChanged()
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // add your code here
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                notes[noteID] = charSequence.toString()
                arrayAdapter.notifyDataSetChanged()
                val sharedPreferences = getApplicationContext<Context>().getSharedPreferences(
                    "jcb.bb.jowdi",
                    Context.MODE_PRIVATE
                )
                val set: HashSet<String> = HashSet(notes)
                sharedPreferences.edit().putStringSet("notes", set).apply()
            }

            override fun afterTextChanged(editable: Editable) {
                // add your code here
            }
        })

    }


}
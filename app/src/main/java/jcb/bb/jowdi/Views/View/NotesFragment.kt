package jcb.bb.jowdi.Views.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import jcb.bb.jowdi.databinding.FragmentNotesBinding

import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import jcb.bb.jowdi.Adapter.AdapterOnClick
import jcb.bb.jowdi.Adapter.NotesAdapter
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.Views.Viewmodel.ViewModel

class NotesFragment : Fragment(), AdapterOnClick {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var rview: RecyclerView
    private lateinit var model: ViewModel
    private var datafav = ArrayList<ListDataModel>()

    lateinit var arrayAdapter: NotesAdapter
    lateinit var title : EditText
    lateinit var desc : EditText

    lateinit var titleText :String
    lateinit var descText :String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        initialize()
        firstactivity()
        retrieve()
        add()
        return binding.root
    }


    private fun initialize() {
        rview = binding.listView
        title = binding.title
        desc = binding.desc

    }

    fun getAllData() {
        datafav.clear()
        model.readAllData.observe(viewLifecycleOwner, { data ->
            rview.visibility = View.VISIBLE
            rview.alpha = 0f
            rview.animate().setDuration(300).alpha(1f).withEndAction {
                for (item in data) {
                    if (item.category == "notes") {
                        datafav.add(item)
                        arrayAdapter = NotesAdapter(datafav, this)
                        rview.adapter = arrayAdapter
                    }
                }
            }
        })
    }


    fun firstactivity() {
        getAllData()

        binding.addNote.setOnClickListener {
            binding.firstLayout.visibility = View.GONE
            binding.secondLayout.visibility = View.VISIBLE

            binding.save.setOnClickListener{
                add()
            }
        }

    }

    fun delete() {

    }

    fun add() {
        titleText = binding.title.editableText.toString()
        descText = binding.desc.editableText.toString()
    }

    fun retrieve() {

    }


    override fun onAdapterClick(positon: Int) {


    }


}
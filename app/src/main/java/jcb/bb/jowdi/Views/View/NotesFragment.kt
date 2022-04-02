package jcb.bb.jowdi.Views.View

import android.R
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import jcb.bb.jowdi.databinding.FragmentNotesBinding

import androidx.recyclerview.widget.RecyclerView

import jcb.bb.jowdi.Adapter.AdapterOnClick
import jcb.bb.jowdi.Adapter.NotesAdapter
import jcb.bb.jowdi.ApiConnection.UserRepository
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.Views.Viewmodel.ViewModel
import android.content.DialogInterface




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

    lateinit var value1 :String
    lateinit var value2 :String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        initialize()
        firstactivity()

        return binding.root
    }


    private fun initialize() {
        model = ViewModelProvider(this).get(ViewModel::class.java)
        rview = binding.listView.apply {
            layoutManager = LinearLayoutManager(
                context,  LinearLayoutManager.VERTICAL,
                false)
        }
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
                            if (datafav.isEmpty()) {
                                Toast.makeText(context, "no record!", Toast.LENGTH_SHORT).show()
                            }
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

                Toast.makeText(context,"Saved!",Toast.LENGTH_SHORT).show()
                binding.firstLayout.visibility = View.VISIBLE
                binding.secondLayout.visibility = View.GONE

            }
        }

    }
    fun add(){
        datafav.clear()
        model.addNote(binding.title.text.toString(), binding.desc.text.toString())
    }

    override fun onAdapterClick(positon: Int, name: String) {
        if (name == "edit"){
        binding.firstLayout.visibility = View.GONE
        binding.secondLayout.visibility = View.VISIBLE

        value1 = datafav[positon].title
        value2 = datafav[positon].desc

        Toast.makeText(context,"$value1 , $value2",Toast.LENGTH_SHORT).show()

        binding.title.setText(value1)
        binding.desc.setText(value2)

        binding.save.setOnClickListener{
            value1 = binding.title.text.toString()
            value2 = binding.desc.text.toString()

            datafav.clear()
            model.updateNote(positon, value1, value2)

            Toast.makeText(context,"Updated!",Toast.LENGTH_SHORT).show()
            binding.firstLayout.visibility = View.VISIBLE
            binding.secondLayout.visibility = View.GONE
            getAllData()
            }
        }else{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)

            builder
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(
                    R.string.yes
                ) { _, _ ->
                    datafav.clear()
                    model.onClearData(positon)
                    Toast.makeText(context,"Deleted!",Toast.LENGTH_SHORT).show()
                    getAllData()
                } // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(R.string.no, null)
                .setIcon(R.drawable.ic_dialog_alert)
                .show()
        }



    }


}
package jcb.bb.jowdi.Views.View

import android.app.AlertDialog
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import jcb.bb.jowdi.databinding.FragmentNotesBinding

import androidx.recyclerview.widget.RecyclerView

import jcb.bb.jowdi.Adapter.NotesAdapter
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.Views.Viewmodel.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.SnapshotHolder
import com.google.gson.Gson
import jcb.bb.jowdi.Adapter.StringOnClick
import jcb.bb.jowdi.Views.Model.ListModel
import jcb.bb.jowdi.Views.Model.NotesModel
import jcb.bb.jowdi.database.FirebaseDB
import com.google.firebase.ktx.Firebase
import jcb.bb.jowdi.R


class NotesFragment : Fragment(), StringOnClick {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var rview: RecyclerView
    private lateinit var model: ViewModel
    private var datafav = ArrayList<ListDataModel>()
    private var notesModel = ArrayList<NotesModel>()
    lateinit var datamodel: ListDataModel

    var keykey: Array<String> = arrayOf()

    lateinit var arrayAdapter: NotesAdapter
    lateinit var title: EditText
    lateinit var desc: EditText

    lateinit var titleText: String
    lateinit var descText: String

    lateinit var value1: String
    lateinit var value2: String

    var fb = FirebaseDB()
    var gson = Gson()
    lateinit var item: DataSnapshot

    var bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotesBinding.inflate(inflater, container, false)

        initialize()
        return binding.root
    }


    private fun initialize() {
        model = ViewModelProvider(this).get(ViewModel::class.java)
        rview = binding.listView.apply {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL,
                false
            )
        }

        title = binding.title
        desc = binding.desc

        buttonCLick()
        getData()


    }

    fun buttonCLick(){

        binding.addNote.setOnClickListener {
            bundle.putString("btn", "addNote")
            findNavController().navigate(R.id.action_NotesFragment_to_EditNotes, bundle )
        }

    }

    fun getData() {
        binding.firstLayout.visibility = View.VISIBLE
        binding.secondLayout.visibility = View.GONE

        fb.FirebaseDB().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                item = snapshot
                for (item in snapshot.children) {
                    val data: NotesModel? = item.getValue(NotesModel::class.java)
                    if (data != null) {
                        notesModel.add(data)
                        Log.d("GetData: ", gson.toJson(data).toString())
                        arrayAdapter =
                            NotesAdapter(notesModel, this@NotesFragment, requireContext())
                        rview.adapter = arrayAdapter

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onAdapterClick(positon: Int) {
        binding.firstLayout.visibility = View.GONE
        binding.secondLayout.visibility = View.VISIBLE
        var dataKeys = ""
        var counter = 0
        for (child in item.children) {
            if (counter == positon) {
                dataKeys =  child.key!!
                break
            }
            counter++;
        }

        bundle.putString("btn", "update")
        bundle.putString("key", dataKeys)
        findNavController().navigate(R.id.action_NotesFragment_to_EditNotes, bundle )

       // Toast.makeText(context, dataKeys, Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(position: Int, v: View?) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                var dataKeys = ""
                var counter = 0
                fb.FirebaseDB().addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (item in snapshot.children) {
                            if (counter == position) {
                                dataKeys =  item.key!!
                                break
                            }
                            counter++
                        }
                        fb.remove(dataKeys)
                        initialize()
                        Toast.makeText(context,"deleted!!", Toast.LENGTH_SHORT).show()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }


}
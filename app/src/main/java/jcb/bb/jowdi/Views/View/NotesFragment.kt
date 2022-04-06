package jcb.bb.jowdi.Views.View

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
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
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.Views.Viewmodel.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import jcb.bb.jowdi.Adapter.StringOnClick
import jcb.bb.jowdi.Views.Model.ListModel
import jcb.bb.jowdi.Views.Model.NotesModel
import jcb.bb.jowdi.database.FirebaseDB


class NotesFragment : Fragment(), StringOnClick {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var rview: RecyclerView
    private lateinit var model: ViewModel
    private var datafav = ArrayList<ListDataModel>()
    private var notesModel = ArrayList<NotesModel>()
    lateinit var datamodel: ListDataModel

    lateinit var listd: List<NotesModel>

    lateinit var arrayAdapter: NotesAdapter
    lateinit var title: EditText
    lateinit var desc: EditText

    lateinit var titleText: String
    lateinit var descText: String

    lateinit var value1: String
    lateinit var value2: String

    var fb = FirebaseDB()
    var gson = Gson()
    var keyy: Array<String> = arrayOf()
    lateinit var item: DataSnapshot

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotesBinding.inflate(inflater, container, false)

        initialize()
        firstactivity()

        getData()

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

    }

    fun getData() {
        fb.FirebaseDB().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                item = snapshot
                for (item in snapshot.children) {
                    val data: NotesModel? = item.getValue(NotesModel::class.java)
                    if (data != null) {
                        notesModel.add(data)
                        Log.d("GetData: ", gson.toJson(data).toString())

                        arrayAdapter = NotesAdapter(notesModel, this@NotesFragment , requireContext())
                        rview.adapter = arrayAdapter

                    } else {
                        Toast.makeText(context, "no record!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun firstactivity() {

        //getAllData()

        binding.addNote.setOnClickListener {
            binding.firstLayout.visibility = View.GONE
            binding.secondLayout.visibility = View.VISIBLE

            binding.save.setOnClickListener {
                add()
                //Toast.makeText(context,"Saved!",Toast.LENGTH_SHORT).show()
                binding.firstLayout.visibility = View.VISIBLE
                binding.secondLayout.visibility = View.GONE

            }
        }

    }

    fun add() {

        value1 = binding.title.editableText.toString()
        value2 = binding.desc.editableText.toString()

        val listModel = ListModel( "notes",value2, "",  value1)
        fb.add(listModel).addOnSuccessListener {
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
        }
        binding.firstLayout.visibility = View.VISIBLE
        binding.secondLayout.visibility = View.GONE
    }

    override fun onAdapterClick(positon: Int) {
        var dataKeys = ""
        var counter = 0

         for (child in item.children) {
            if (counter == positon) {
                dataKeys = dataKeys + child.key + ""
                break
            }
            counter++;
        }
        Toast.makeText(context, dataKeys, Toast.LENGTH_SHORT).show()

        binding.firstLayout.visibility = View.GONE
        binding.secondLayout.visibility = View.VISIBLE

        value1 = binding.title.editableText.toString()
        value2 = binding.desc.editableText.toString()

        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["category"] = "notes"
        hashMap["image"] = ""
        hashMap["title"] = value1
        hashMap["desc"] = value2

        binding.save.setOnClickListener {
            fb.update(dataKeys, hashMap).addOnSuccessListener {
                Toast.makeText(context, hashMap.toString(), Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
            }
            binding.firstLayout.visibility = View.VISIBLE
            binding.secondLayout.visibility = View.GONE
        }
    }

    override fun onItemLongClick(position: Int, v: View?) {

    }


}
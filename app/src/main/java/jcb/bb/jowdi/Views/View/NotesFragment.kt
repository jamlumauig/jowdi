package jcb.bb.jowdi.Views.View

import android.annotation.SuppressLint
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
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import jcb.bb.jowdi.databinding.FragmentNotesBinding

import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task

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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseReference


class NotesFragment : Fragment(), StringOnClick {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var rview: RecyclerView
    private lateinit var model: ViewModel
    private var notesModel = ArrayList<NotesModel>()

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        initialize()
        fb.FirebaseDB()
        btnClick()

        return binding.root
    }

    fun btnClick() {
        binding.addNote.setOnClickListener {
            binding.firstLayout.visibility = View.GONE
            binding.secondLayout.visibility = View.VISIBLE

            binding.save.setOnClickListener {
                add()
            }
        }
    }

    private fun initialize() {
        title = binding.title
        desc = binding.desc

        rview = binding.listView
        rview.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL,
            false
        )
        getData()
    }


    fun getData() {

        binding.firstLayout.visibility = View.VISIBLE
        binding.secondLayout.visibility = View.GONE
        notesModel.clear()

        fb.FirebaseDB().addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                item = snapshot
                for (item in snapshot.children) {
                    val data: NotesModel? = item.getValue(NotesModel::class.java)
                    if (data != null) {
                        notesModel.add(data)
                        Log.d("GetData: ", gson.toJson(data).toString())
                        arrayAdapter =
                            NotesAdapter(notesModel, this@NotesFragment)
                        rview.adapter = arrayAdapter
                        arrayAdapter.notifyDataSetChanged()

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun add() {
        value1 = binding.title.editableText.toString()
        value2 = binding.desc.editableText.toString()

        val listModel = ListModel("notes", value2, "", value1)
        fb.add(listModel).addOnSuccessListener {
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
            getData()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAdapterClick(positon: Int) {
        notesModel.clear()
        binding.firstLayout.visibility = View.GONE
        binding.secondLayout.visibility = View.VISIBLE
        var dataKeys = ""
        var counter = 0

        fb.FirebaseDB().addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    if (counter == positon) {
                        dataKeys = item.key!!
                        break
                    }
                    counter++
                }

                Toast.makeText(context, dataKeys, Toast.LENGTH_SHORT).show()

                value1 = binding.title.editableText.toString()
                value2 = binding.desc.editableText.toString()

                val hashMap: HashMap<String, Any> = HashMap()
                hashMap["category"] = "notes"
                hashMap["image"] = ""
                hashMap["title"] = value1
                hashMap["desc"] = value2

                Log.d("UPDATE1 ", "$value1 $value2")

                binding.save.setOnClickListener {
                    Log.d("UPDATE2 ", "$value1 $value2")

                    /*   fb.update(dataKeys, hashMap).addOnSuccessListener {
                           Log.d("UPDATE2 ", "$value1 $value2")
                           // Toast.makeText(context, hashMap.toString(), Toast.LENGTH_SHORT).show()
                       }.addOnFailureListener {
                           Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                       }*/
                    getData()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onItemLongClick(position: Int, v: View?) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                var dataKeys = ""
                var counter = 0
                fb.FirebaseDB().addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (item in snapshot.children) {
                            if (counter == position) {
                                dataKeys = item.key!!
                                break
                            }
                            counter++
                        }

                        fb.remove(dataKeys)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("LOL TAG", "$dataKeys Element removed successfully!")
                                    Toast.makeText(
                                        requireContext(),
                                        "deleted!!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    getData()

                                }
                            }
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
        getData()
    }


}
package jcb.bb.jowdi.Views.View

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView

import jcb.bb.jowdi.Adapter.NotesAdapter
import jcb.bb.jowdi.Views.Viewmodel.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import jcb.bb.jowdi.Adapter.StringOnClick
import jcb.bb.jowdi.Views.Model.NotesModel
import jcb.bb.jowdi.database.FirebaseDB
import jcb.bb.jowdi.databinding.FragmentNotesBinding

class NotesFragment : Fragment(), StringOnClick {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var rview: RecyclerView
    private lateinit var model: ViewModel
    private var notesModel = ArrayList<NotesModel>()

    var keykey: Array<String> = arrayOf()

    var arrayAdapter: NotesAdapter? = null
    lateinit var title: EditText
    lateinit var desc: EditText

    lateinit var titleText: String
    lateinit var descText: String

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
                if (snapshot.exists()) {
                    for (item in snapshot.children) {
                        val data: NotesModel? = item.getValue(NotesModel::class.java)
                        if (data != null) {
                            notesModel.add(data)
                            Log.d("GetData: ", gson.toJson(data).toString())
                            arrayAdapter =
                                NotesAdapter(notesModel, this@NotesFragment)
                            rview.adapter = arrayAdapter
                            arrayAdapter?.notifyDataSetChanged()

                        }
                    }
                } else {
                    notesModel.clear()
                    arrayAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun add() {
        binding.apply {
            val data = NotesModel("", "notes",
                desc.text.toString(), "", title.text.toString())

            fb.add(data)
                .addOnSuccessListener {
                    Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                    getData()
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onAdapterClick(positon: Int) {
        binding.apply {
            firstLayout.visibility = View.GONE
            secondLayout.visibility = View.VISIBLE

            notesModel[positon].apply {
                this@NotesFragment.title.setText(title.toString())
                this@NotesFragment.desc.setText(desc.toString())

                binding.save.setOnClickListener {
                    saveData(
                        id = id,
                        title = this@NotesFragment.title.text.toString(),
                        desc = this@NotesFragment.desc.text.toString()
                    )
                }
            }
        }
    }

    private fun saveData(id: String?, title: String?, desc: String?) {

        val list = HashMap<String, Any>()
        list["title"] = title.toString()
        list["desc"] = desc.toString()

        fb.update(id, list).addOnSuccessListener {
            Toast.makeText(context, "UPDATED!!", Toast.LENGTH_SHORT).show()
            getData()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemLongClick(position: Int, v: View?) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure you want to Delete?")
        .setCancelable(false)
        .setPositiveButton("Yes") { _, _ ->

            fb.remove(notesModel[position].id.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "deleted!!",
                        Toast.LENGTH_SHORT
                    ).show()

//                    notesModel.removeAt(position)
//                    arrayAdapter?.notifyItemRemoved(position)
//                    arrayAdapter?.notifyItemRangeChanged(position, notesModel.size);
//
//                    return@addOnCompleteListener

                    getData()
                }
            }
        }
        .setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }
}
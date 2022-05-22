package jcb.bb.jowdi.Views.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import jcb.bb.jowdi.Adapter.ItemArrayAdapter
import jcb.bb.jowdi.R
import jcb.bb.jowdi.Views.Model.NotesModel
import jcb.bb.jowdi.database.FirebaseDB
import jcb.bb.jowdi.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    var fb = FirebaseDB()
    var gson = Gson()
    lateinit var item: DataSnapshot

    private lateinit var rview: RecyclerView
    private var Adapter: ItemArrayAdapter? = null
    private var datafav = ArrayList<NotesModel>()

    lateinit var back: ImageButton
    lateinit var next: ImageButton

    lateinit var title: TextView

    var fromSecond: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)

        initialize()
        btnclick()
        animation()
        return binding.root
    }

    private fun initialize() {
        back = binding.bck
        next = binding.next

        title = binding.text
        fromSecond = arguments?.getString("btn")
    }

    private fun animation() {
        val top_curve_anim = AnimationUtils.loadAnimation(context, R.anim.top_down)
        binding.topCurve.startAnimation(top_curve_anim)

        val field_name_anim = AnimationUtils.loadAnimation(context, R.anim.field_name_anim)
        binding.bck.startAnimation(field_name_anim)
        binding.next.startAnimation(field_name_anim)
        binding.text.startAnimation(field_name_anim)

        val center_reveal_anim = AnimationUtils.loadAnimation(context, R.anim.center_reveal_anim)
        binding.rview.startAnimation(center_reveal_anim)
    }

    private fun btnclick() {
        rview = binding.rview
        rview.layoutManager = GridLayoutManager(
            context, 2, GridLayoutManager.VERTICAL,
            false
        )
        when (fromSecond) {
            "btn1" -> {
                first()
            }
            "btn2" -> {
                second()
            }
            "btn3" -> {
                third()
            }
        }
    }

    private fun first() {
        next.visibility = View.VISIBLE
        back.visibility = View.GONE
        title.text = "First Year with YOU"

        datafav.clear()

        fb.images().child("first").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                item = snapshot
                for (item in snapshot.children) {
                    val data: NotesModel? = item.getValue(NotesModel::class.java)
                    if (data != null) {
                        datafav.add(data)
                        Log.d("GetData: ", gson.toJson(data).toString())
                        Adapter = ItemArrayAdapter(datafav, requireContext())
                        rview.adapter = Adapter
                        Adapter!!.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        next.setOnClickListener {
            second()
        }
    }

    private fun second() {
        datafav.clear()

        back.visibility = View.VISIBLE
        back.setOnClickListener {
            first()
        }
        title.text = "Second Year with YOU"

        fb.images().child("second").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                item = snapshot
                for (item in snapshot.children) {
                    val data: NotesModel? = item.getValue(NotesModel::class.java)
                    if (data != null) {
                        datafav.add(data)
                        Log.d("GetData: ", gson.toJson(data).toString())
                        Adapter = ItemArrayAdapter(datafav, requireContext())
                        rview.adapter = Adapter
                        Adapter!!.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        next.setOnClickListener {
            third()
        }
    }

    private fun third() {
        datafav.clear()

        back.visibility = View.VISIBLE
        back.setOnClickListener {
            second()
        }
        title.text = "Third Year with YOU"

        fb.images().child("third").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                item = snapshot
                for (item in snapshot.children) {
                    val data: NotesModel? = item.getValue(NotesModel::class.java)
                    if (data != null) {
                        datafav.add(data)
                        Log.d("GetData: ", gson.toJson(data).toString())
                        Adapter = ItemArrayAdapter(datafav, requireContext())
                        rview.adapter = Adapter
                        Adapter!!.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        next.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
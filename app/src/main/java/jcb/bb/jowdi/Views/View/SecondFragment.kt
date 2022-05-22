package jcb.bb.jowdi.Views.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import jcb.bb.jowdi.Adapter.Adapter
import jcb.bb.jowdi.R
import jcb.bb.jowdi.Views.Model.NotesModel
import jcb.bb.jowdi.database.FirebaseDB
import jcb.bb.jowdi.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private lateinit var frontrview: RecyclerView
    private lateinit var adapterx: Adapter

    private var notesModel = ArrayList<NotesModel>()

    var fb = FirebaseDB()
    var gson = Gson()
    lateinit var item: DataSnapshot

    lateinit var title: TextView

    var bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        btnclick()
        front()
        animation()
        return binding.root
    }

    private fun btnclick() {
        binding.btn1.setOnClickListener {
            bundle.putString("btn", "btn1")
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle)
        }

        binding.btn2.setOnClickListener {
            bundle.putString("btn", "btn2")
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle)
        }

        binding.btn3.setOnClickListener {
            bundle.putString("btn", "btn3")
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle)
        }

        binding.music.setOnClickListener {
            bundle.putString("btn", "music")
            findNavController().navigate(R.id.action_SecondFragment_to_FourthFragment, bundle)
        }

        binding.notes.setOnClickListener {
            bundle.putString("btn", "notes")
            findNavController().navigate(R.id.action_SecondFragment_to_NotesFragment, bundle)
        }
    }


    private fun animation() {
        val top_curve_anim = AnimationUtils.loadAnimation(context, R.anim.top_down)
        binding.topCurve.startAnimation(top_curve_anim)

        val field_name_anim = AnimationUtils.loadAnimation(context, R.anim.field_name_anim)
        binding.btn1.startAnimation(field_name_anim)
        binding.btn2.startAnimation(field_name_anim)
        binding.btn3.startAnimation(field_name_anim)
        binding.music.startAnimation(field_name_anim)
        binding.notes.startAnimation(field_name_anim)

        val center_reveal_anim = AnimationUtils.loadAnimation(context, R.anim.center_reveal_anim)
        binding.reason.startAnimation(center_reveal_anim)
        binding.message.startAnimation(center_reveal_anim)
        binding.textviewFirst.startAnimation(center_reveal_anim)
        binding.guideline.startAnimation(center_reveal_anim)

        val new_user_anim = AnimationUtils.loadAnimation(context, R.anim.down_top)
        binding.recycler.startAnimation(new_user_anim)
        binding.what.startAnimation(new_user_anim)
    }

    private fun front() {
        frontrview = binding.recycler
        frontrview.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL,
            false
        )
        fb.reasons().addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                item = snapshot
                for (item in snapshot.children) {
                    val data: NotesModel? = item.getValue(NotesModel::class.java)
                    if (data != null) {
                        notesModel.add(data)
                        Log.d("GetData: ", gson.toJson(data).toString())
                        adapterx =
                            Adapter(notesModel, requireContext())
                        frontrview.adapter = adapterx
                        adapterx.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
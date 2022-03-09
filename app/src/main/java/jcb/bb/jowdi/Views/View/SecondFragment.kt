package jcb.bb.jowdi.Views.View

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jcb.bb.jowdi.Adapter.Adapter
import jcb.bb.jowdi.Adapter.ItemArrayAdapter
import jcb.bb.jowdi.R
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.Views.Viewmodel.ViewModel
import jcb.bb.jowdi.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private lateinit var frontrview: RecyclerView

    private lateinit var adapterx: Adapter

    private lateinit var model: ViewModel
    private var datafav = ArrayList<ListDataModel>()
    lateinit var title: TextView

    var bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        initialize()
        btnclick()
        front()
        return binding.root
    }

    private fun initialize(){
        model = ViewModelProvider(this).get(ViewModel::class.java)
    }

    private fun btnclick() {
        binding.btn1.setOnClickListener {
            bundle.putString("btn", "btn1")
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle )
        }

        binding.btn2.setOnClickListener {
            bundle.putString("btn", "btn2")
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle )
        }

        binding.btn3.setOnClickListener {
            bundle.putString("btn", "btn3")
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle )
        }
    }

    private fun front() {
        frontrview = binding.recycler
        frontrview.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL,
            false
        )
        model.readAllData.observe(viewLifecycleOwner, { datas ->
            frontrview.visibility = View.VISIBLE
            frontrview.alpha = 0f
            frontrview.animate().setDuration(1000).alpha(1f).withEndAction {
                for (items in datas) {
                    if (items.category == "reasons") {
                        datafav.add(items)
                        adapterx = Adapter(datafav, requireContext())
                        frontrview.adapter = adapterx
                    }
                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
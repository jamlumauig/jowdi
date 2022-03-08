package jcb.bb.jowdi.Views.View

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
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

    private lateinit var rview: RecyclerView
    private lateinit var frontrview: RecyclerView

    private lateinit var adapterx: Adapter
    private var Adapter: ItemArrayAdapter? = null

    private lateinit var model: ViewModel
    private var datafav = ArrayList<ListDataModel>()

    lateinit var back: ImageButton
    lateinit var next: ImageButton

    lateinit var title: TextView

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
        binding.linear1.visibility = View.VISIBLE
        binding.linear2.visibility = View.GONE

        back = binding.bck
        next = binding.next

        title = binding.text
        model = ViewModelProvider(this).get(ViewModel::class.java)
    }

    private fun btnclick() {
        rview = binding.rview
        rview.layoutManager = GridLayoutManager(
            context, 2, GridLayoutManager.VERTICAL,
            false
        )
        binding.btn1.setOnClickListener {
            binding.linear2.visibility = View.VISIBLE
            binding.linear1.visibility = View.GONE
            first()
        }

        binding.btn2.setOnClickListener {
            binding.linear2.visibility = View.VISIBLE
            binding.linear1.visibility = View.GONE
            second()
        }

        binding.btn3.setOnClickListener {
            binding.linear2.visibility = View.VISIBLE
            binding.linear1.visibility = View.GONE
            third()
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

    private fun first() {
        next.visibility = View.VISIBLE
        back.visibility = View.GONE
        title.text = "First Year with YOU"

        datafav.clear()
        model.readAllData.observe(viewLifecycleOwner, { data ->
            rview.visibility = View.VISIBLE
            rview.alpha = 0f
            rview.animate().setDuration(300).alpha(1f).withEndAction {
                for (item in data) {
                    if (item.category == "first") {
                        datafav.add(item)
                        Adapter = ItemArrayAdapter(datafav, requireContext())
                        rview.adapter = Adapter
                    }
                }
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
        model.readAllData.observe(viewLifecycleOwner, { data ->
            rview.visibility = View.VISIBLE
            rview.alpha = 0f
            rview.animate().setDuration(300).alpha(1f).withEndAction {
                for (item in data) {
                    if (item.category == "second") {
                        datafav.add(item)
                        Adapter = ItemArrayAdapter(datafav, requireContext())
                        rview.adapter = Adapter
                    }
                }
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

        model.readAllData.observe(viewLifecycleOwner, { data ->
            rview.visibility = View.VISIBLE
            rview.alpha = 0f
            rview.animate().setDuration(300).alpha(1f).withEndAction {
                for (item in data) {
                    if (item.category == "third") {
                        datafav.add(item)
                        Adapter = ItemArrayAdapter(datafav, requireContext())
                        rview.adapter = Adapter
                    }
                }
            }
        })
        next.visibility = View.GONE
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
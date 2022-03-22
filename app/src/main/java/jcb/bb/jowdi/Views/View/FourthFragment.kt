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
import jcb.bb.jowdi.databinding.FragmentFourthBinding
import jcb.bb.jowdi.databinding.FragmentSecondBinding

class FourthFragment : Fragment() {

    private var _binding: FragmentFourthBinding? = null
    private val binding get() = _binding!!

    private lateinit var frontrview: RecyclerView

    private lateinit var adapterx: Adapter

    private lateinit var model: ViewModel
    private var datafav = ArrayList<ListDataModel>()
    lateinit var title: TextView

    var fromSecond: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        initialize()
        btnclick()
        fromSecond = arguments?.getString("btn")

        return binding.root
    }

    private fun initialize(){
        model = ViewModelProvider(this).get(ViewModel::class.java)

    }

    private fun btnclick() {

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
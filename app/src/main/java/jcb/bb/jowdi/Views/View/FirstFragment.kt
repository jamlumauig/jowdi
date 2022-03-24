package jcb.bb.jowdi.Views.View

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.media.MediaPlayer.create
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import jcb.bb.jowdi.R
import jcb.bb.jowdi.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    var b = Bundle()
    private lateinit var result: EditText

    private val mp: MediaPlayer = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        mp.setDataSource("https://firebasestorage.googleapis.com/v0/b/jowdi-project.appspot.com/o/music%2Fhbd.mp3?alt=media&token=e5f75db6-d92d-453c-8100-e296d962bcb6")
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        result = binding.editText
        binding.firstLayout.visibility = View.VISIBLE
        binding.secondLayout.visibility = View.GONE

        binding.buttonFirst.setOnClickListener {
            if (binding.editText.editableText.toString() == "JODEL" ||
                binding.editText.editableText.toString() == "MAHAL" ||
                binding.editText.editableText.toString() == "j"

            ) {
                binding.firstLayout.visibility = View.GONE
                binding.secondLayout.visibility = View.VISIBLE
                mp.prepareAsync()
                mp.setOnPreparedListener { mp.start() }

                binding.btn.setOnClickListener {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, b)
                    mp.stop()
                    mp.reset()
                }
            } else {
                Toast.makeText(context, "DI KITA LAB HAHA\nPLEASE ENTER AGAIN", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mp.stop()
        _binding = null
    }
}
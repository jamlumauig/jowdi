package jcb.bb.jowdi.Views.View

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jcb.bb.jowdi.Adapter.AdapterOnClick
import jcb.bb.jowdi.Adapter.MusicAdapter
import jcb.bb.jowdi.R
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.Views.Viewmodel.ViewModel
import jcb.bb.jowdi.databinding.FragmentFourthBinding
import android.media.MediaPlayer.OnPreparedListener
import android.os.Handler
import android.widget.SeekBar
import android.widget.Toast
import java.io.IOException


class FourthFragment : Fragment(), AdapterOnClick {

    private var _binding: FragmentFourthBinding? = null
    private val binding get() = _binding!!

    private lateinit var rview: RecyclerView

    private lateinit var adapterx: MusicAdapter

    private lateinit var model: ViewModel
    private var datafav = ArrayList<ListDataModel>()
    lateinit var title: TextView

    var fromSecond: String? = null
    lateinit var SongTitle: String
    lateinit var SongLink: String

    private val mp: MediaPlayer = MediaPlayer()

    lateinit var linear2: LinearLayout
    lateinit var music: LinearLayout

    lateinit var seek: SeekBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        initialize()

        return binding.root
    }

    private fun initialize() {
        model = ViewModelProvider(this).get(ViewModel::class.java)
        linear2 = binding.linear2
        music = binding.music
        seek = binding.seekBar
        music.visibility = View.GONE
        linear2.visibility = View.VISIBLE
        rview = binding.rview
        rview.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL,
            false
        )

        binding.back.setOnClickListener{
            music.visibility = View.GONE
            linear2.visibility = View.VISIBLE
            mp.stop()
            mp.reset()
        }

        run()
    }

    fun run() {
        datafav.clear()
        model.readAllData.observe(viewLifecycleOwner, { data ->
            rview.visibility = View.VISIBLE
            rview.alpha = 0f
            rview.animate().setDuration(300).alpha(1f).withEndAction {
                for (item in data) {
                    if (item.category == "music") {
                        datafav.add(item)
                        adapterx = MusicAdapter(datafav, this)
                        rview.adapter = adapterx
                    }
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mp.stop()
        _binding = null
    }


    private fun initializeseek() {
        seek.max = mp.duration

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seek.progress = mp.currentPosition
                    handler.postDelayed(this, 500)
                } catch (e: Exception) {
                    seek.progress = 0
                }
            }
        }, 0)
    }

    private fun setSong() {
        try {

            seek.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seek: SeekBar,
                    progress: Int, fromUser: Boolean
                ) {
                    if (fromUser) mp.seekTo(progress)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onAdapterClick(positon: Int) {
        var position = positon
        SongTitle = datafav[position].desc
        SongLink = datafav[position].image
        Log.e("SongTITLE", SongTitle)

        music.visibility = View.VISIBLE
        linear2.visibility = View.GONE

        binding.title.text = SongTitle
        setSong()
        mp.setDataSource(SongLink)
        mp.prepareAsync()
        mp.setOnPreparedListener {
            initializeseek()
            mp.start()
        }
        binding.play.setOnClickListener {
            seek.max = mp.duration
            if (mp.isPlaying) {
                mp.pause()
                binding.play.setImageResource(R.drawable.play)
            } else {
                mp.start()
                binding.play.setImageResource(R.drawable.pause)
            }
        }

        binding.next.setOnClickListener {
            music.visibility = View.VISIBLE
            linear2.visibility = View.GONE

            if (++position >= datafav.size) {
                position = 0
            } else
                try {
                    mp.stop()
                    mp.reset()
                    SongTitle = datafav[position].desc
                    SongLink = datafav[position].image
                    binding.title.text = SongTitle
                    mp.setDataSource(SongLink)
                    mp.prepareAsync()
                    mp.setOnPreparedListener {
                        initializeseek()
                        mp.start()
                        binding.play.setImageResource(R.drawable.pause)
                    }

                    Log.e("SongLink", SongLink)

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

        }
        binding.prev.setOnClickListener {
            music.visibility = View.VISIBLE
            linear2.visibility = View.GONE

            if (--position >= datafav.size) {
                position = 0
            } else
                try {
                    mp.stop()
                    mp.reset()
                    SongTitle = datafav[position].desc
                    SongLink = datafav[position].image
                    binding.title.text = SongTitle
                    mp.setDataSource(SongLink)
                    mp.prepareAsync()
                    mp.setOnPreparedListener {
                        initializeseek()
                        mp.start()
                        binding.play.setImageResource(R.drawable.pause)
                    }

                    Log.e("SongLink", SongLink)

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
        }

    }
}
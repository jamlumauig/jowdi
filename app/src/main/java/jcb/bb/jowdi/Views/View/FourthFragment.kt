package jcb.bb.jowdi.Views.View

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jcb.bb.jowdi.Adapter.AdapterOnClick
import jcb.bb.jowdi.Adapter.MusicAdapter
import jcb.bb.jowdi.R
import jcb.bb.jowdi.databinding.FragmentFourthBinding
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import jcb.bb.jowdi.Views.Model.NotesModel
import jcb.bb.jowdi.database.FirebaseDB

class FourthFragment : Fragment(), AdapterOnClick {

    private var _binding: FragmentFourthBinding? = null
    private val binding get() = _binding!!

    private lateinit var rview: RecyclerView
    private lateinit var adapterx: MusicAdapter

    private var datafav = ArrayList<NotesModel>()
    lateinit var title: TextView

    var fb = FirebaseDB()
    var gson = Gson()
    lateinit var item: DataSnapshot

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
        animation()
        return binding.root
    }

    private fun animation() {
        val top_curve_anim = AnimationUtils.loadAnimation(context, R.anim.top_down)
        binding.title.startAnimation(top_curve_anim)

        val field_name_anim = AnimationUtils.loadAnimation(context, R.anim.field_name_anim)
        binding.rview.startAnimation(field_name_anim)
    }

    private fun initialize() {
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

        binding.back.setOnClickListener {
            music.visibility = View.GONE
            linear2.visibility = View.VISIBLE
            mp.stop()
            mp.reset()
        }
        binding.back2.setOnClickListener {
            findNavController().navigate(R.id.action_FourthFragment_to_SecondFragment)
        }

        run()
    }

    fun run() {
        datafav.clear()
        fb.musics().addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                item = snapshot
                for (item in snapshot.children) {
                    val data: NotesModel? = item.getValue(NotesModel::class.java)
                    if (data != null) {
                        datafav.add(data)
                        Log.d("GetData: ", gson.toJson(data).toString())
                        adapterx =
                            MusicAdapter(datafav, this@FourthFragment)
                        rview.adapter = adapterx
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
        mp.stop()
        mp.reset()
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
        SongTitle = datafav[position].desc.toString()
        SongLink = datafav[position].image.toString()
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
                    SongTitle = datafav[position].desc.toString()
                    SongLink = datafav[position].image.toString()
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
                    SongTitle = datafav[position].desc.toString()
                    SongLink = datafav[position].image.toString()
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
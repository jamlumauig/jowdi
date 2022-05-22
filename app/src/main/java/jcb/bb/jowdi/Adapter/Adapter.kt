package jcb.bb.jowdi.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jcb.bb.jowdi.R
import jcb.bb.jowdi.Views.Model.NotesModel
import jcb.bb.jowdi.databinding.CardBinding

class Adapter(private var mainlist: ArrayList<NotesModel>, private val context: Context) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {


    class ViewHolder(binding: CardBinding) : RecyclerView.ViewHolder(binding.root) {
        var bindings: CardBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.card, parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mainlist[position]

        val frontSide = holder.bindings.frontSide
        val backSide = holder.bindings.backSide
        val flipView = holder.bindings.flipView

        frontSide.text = data.title

        backSide.setOnClickListener {
            frontSide.text = data.title
            flipView.flipDuration = 1000
            flipView.flipTheView()
        }

        frontSide.setOnClickListener {
            backSide.text = data.desc
            flipView.flipDuration = 1000
            flipView.flipTheView()
        }

    }

    override fun getItemCount(): Int {
        return mainlist.size
    }
}
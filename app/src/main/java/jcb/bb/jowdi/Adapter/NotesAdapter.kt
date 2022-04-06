package jcb.bb.jowdi.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jcb.bb.jowdi.R
import jcb.bb.jowdi.Views.Model.NotesModel
import jcb.bb.jowdi.databinding.NotesssBinding


class NotesAdapter(var mainlist: ArrayList<NotesModel>, var ideaClick: AdapterOnClick) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private val lastClickedItemPosition = 0

    class ViewHolder(binding: NotesssBinding,  var clickData: AdapterOnClick) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        var bindings: NotesssBinding = binding
        fun bindIdea(dataPor: NotesModel) {

            itemView.setOnClickListener(this)
            itemView.apply {
                bindings.title.text = dataPor.title
            }
        }

        override fun onClick(v: View?) {
            clickData.onAdapterClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.notesss, parent, false
            ), ideaClick
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindIdea(mainlist[position])
    }

    fun getLastClickedItemPosition(): Int {
        return lastClickedItemPosition
    }
        override fun getItemCount(): Int {
            return mainlist.size
        }
    }








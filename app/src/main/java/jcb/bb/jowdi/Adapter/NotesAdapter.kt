package jcb.bb.jowdi.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jcb.bb.jowdi.R
import jcb.bb.jowdi.Views.Model.NotesModel
import jcb.bb.jowdi.databinding.NotesssBinding

class NotesAdapter(
    var mainlist: ArrayList<NotesModel>,
    var ideaClick: StringOnClick
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    class ViewHolder(
        binding: NotesssBinding,
        var LongClick: StringOnClick,
        var clickData: StringOnClick
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnLongClickListener, View.OnClickListener {
        var bindings: NotesssBinding = binding
        fun bindIdea(dataPor: NotesModel) {

            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)

            itemView.apply {
                bindings.title.text = dataPor.title
            }
        }

        override fun onClick(v: View?) {
            clickData.onAdapterClick(adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            LongClick.onItemLongClick(adapterPosition, p0)
            return true
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.notesss, parent, false
            ), ideaClick, ideaClick
        )

    override fun onBindViewHolder(
        @NonNull holder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.bindIdea(mainlist[position])

        when {
            position % 2 == 0 -> {
                holder.bindings.listView.setBackgroundColor(Color.parseColor("#87cefe"))
            }
            position % 3 == 0 -> {
                holder.bindings.listView.setBackgroundColor(Color.parseColor("#ee88bb"))
            }
            else -> {
                holder.bindings.listView.setBackgroundColor(Color.parseColor("#392a50"))
            }
        }
    }

    override fun getItemCount(): Int {
        return mainlist.size
    }
}








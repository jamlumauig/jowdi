package jcb.bb.jowdi.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jcb.bb.jowdi.R
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.databinding.MusicBinding
import jcb.bb.jowdi.databinding.NotesssBinding


class NotesAdapter(var mainlist: ArrayList<ListDataModel>, var ideaClick: AdapterOnClick) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    class ViewHolder(binding: NotesssBinding, var clickData: AdapterOnClick) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        var bindings: NotesssBinding = binding
        fun bindIdea(dataPor: ListDataModel) {

            //itemView.setOnClickListener(this)
            itemView.apply {
                bindings.title.text = dataPor.title
            }
        }

        override fun onClick(v: View?) {
            //clickData.onAdapterClick(adapterPosition)
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

        holder.bindings.edit.setOnClickListener {
            holder.clickData.onAdapterClick(position,"edit" )
        }
        holder.bindings.delete.setOnClickListener {
            holder.clickData.onAdapterClick(position ,"delete")
        }
    }

    override fun getItemCount(): Int {
        return mainlist.size
    }
}





package jcb.bb.jowdi.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jcb.bb.jowdi.R
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.databinding.MusicBinding


class MusicAdapter(var mainlist: ArrayList<ListDataModel>, var ideaClick: AdapterOnClick) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    class ViewHolder(binding: MusicBinding, var clickData: AdapterOnClick) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        var bindings: MusicBinding = binding
        fun bindIdea(dataPor: ListDataModel) {

            itemView.setOnClickListener(this)
            itemView.apply {

                bindings.title.text = dataPor.desc
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
                R.layout.music, parent, false
            ), ideaClick
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindIdea(mainlist[position])
    }

    override fun getItemCount(): Int {
        return mainlist.size
    }
}

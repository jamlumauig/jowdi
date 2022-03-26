package jcb.bb.jowdi.Adapter

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jcb.bb.jowdi.R
import jcb.bb.jowdi.databinding.MusicBinding


class NotesAdapter(var mainlist: ArrayList<String>, var ideaClick: AdapterOnClick) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>(), ListAdapter {

    class ViewHolder(binding: MusicBinding, var clickData: AdapterOnClick) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        var bindings: MusicBinding = binding
        fun bindIdea(dataPor: String) {

            itemView.setOnClickListener(this)
            itemView.apply {

                bindings.title.text = dataPor
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

    override fun getItemCount(): Int {
        return mainlist.size
    }

    override fun registerDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun areAllItemsEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(p0: Int): Boolean {
        TODO("Not yet implemented")
    }
}

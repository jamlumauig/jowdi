package jcb.bb.jowdi.Adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.databinding.CardviewtxtBinding

class RVAdapter() : RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    class ViewHolder(binding: CardviewtxtBinding) : RecyclerView.ViewHolder(binding.root) {
        var bindings: CardviewtxtBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RVAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
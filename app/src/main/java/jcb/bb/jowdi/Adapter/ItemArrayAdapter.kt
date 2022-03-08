package jcb.bb.jowdi.Adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import jcb.bb.jowdi.R
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.databinding.CardviewBinding

class ItemArrayAdapter(private var items: List<ListDataModel>, private val context: Context) :
    RecyclerView.Adapter<ItemArrayAdapter.ViewHolder>() {

    private lateinit var dialog: Dialog

    class ViewHolder(binding: CardviewBinding) : RecyclerView.ViewHolder(binding.root) {
        var bindings: CardviewBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.cardview, parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]

        Glide.with(context)
            .load(data.image)
            .apply(RequestOptions.fitCenterTransform())
            .into(holder.bindings.image)

        holder.itemView.setOnClickListener {
            dialog = Dialog(context)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.CENTER)
            dialog.setContentView(R.layout.dialog)

            val img = dialog.findViewById<ImageView>(R.id.img)
            Glide.with(context)
                .load(data.image)
                .apply(RequestOptions.fitCenterTransform())
                .into(img)
            dialog.show()
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}
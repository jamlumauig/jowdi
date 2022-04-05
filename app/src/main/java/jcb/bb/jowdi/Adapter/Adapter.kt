package jcb.bb.jowdi.Adapter
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import jcb.bb.jowdi.R
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.databinding.CardviewBinding
import jcb.bb.jowdi.databinding.CardviewtxtBinding

class Adapter<T>(private var mainlist: List<ListDataModel>, private val context: Context) :
    RecyclerView.Adapter<Adapter<Any?>.ViewHolder>() {

    private lateinit var dialog: Dialog

    class ViewHolder(binding: CardviewtxtBinding) : RecyclerView.ViewHolder(binding.root) {
        var bindings: CardviewtxtBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.cardviewtxt, parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mainlist[position]

       holder.bindings.title.text = data.title

        holder.itemView.setOnClickListener {

            dialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
            dialog.setContentView(R.layout.bottom)

            val sub = dialog.findViewById<TextView>(R.id.titled)
            val des = dialog.findViewById<TextView>(R.id.subtitled)

            sub.text = data.title
            des.text = data.desc

            dialog.show()
        }
    }

    override fun getItemCount(): Int {
        return mainlist.size
    }
}
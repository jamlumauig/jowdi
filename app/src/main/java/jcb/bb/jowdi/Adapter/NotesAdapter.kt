package jcb.bb.jowdi.Adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jcb.bb.jowdi.R
import jcb.bb.jowdi.Views.Model.NotesModel
import jcb.bb.jowdi.database.FirebaseDB
import jcb.bb.jowdi.databinding.NotesssBinding
import com.google.firebase.database.DataSnapshot


class NotesAdapter(
    var mainlist: ArrayList<NotesModel>,
    var key: ArrayList<String>,
    var ideaClick: StringOnClick,
    var context: Context
) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {


    class ViewHolder(binding: NotesssBinding, var clickData: StringOnClick) :
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

    fun setfirebase(){
       val fb  = FirebaseDB()
        fb.databaseReference.key
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        holder.bindIdea(mainlist[position])
        holder.itemView.setOnLongClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    val fb  = FirebaseDB()
                    fb.databaseReference.key

                    //Toast.makeText(context, fb.toString(), Toast.LENGTH_SHORT).show()

                    for (child in fb.children) {
                        if (counter == position) {
                            dataKeys = dataKeys + child.key + ""
                            break
                        }
                        counter++;
                    }
                    Toast.makeText(context, dataKeys, Toast.LENGTH_SHORT).show()


                    /*  if (key != null) {
                          fb.remove(key)
                          Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()

                      }*/
                }
                .setNegativeButton("No") { dialog, _ ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
            true
        }
    }

    override fun getItemCount(): Int {
        return mainlist.size
    }
}








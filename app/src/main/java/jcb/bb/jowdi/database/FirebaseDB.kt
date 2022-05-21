package jcb.bb.jowdi.database

import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.gson.Gson
import jcb.bb.jowdi.Adapter.NotesAdapter
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.Views.Model.ListModel
import jcb.bb.jowdi.Views.Model.NotesModel
import java.text.FieldPosition

class FirebaseDB {
    lateinit var databaseReference : DatabaseReference

    fun FirebaseDB() : DatabaseReference {
        val database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Jowdi")
        return databaseReference
    }

     fun add(listModel: NotesModel): Task<Void> {
         val keyId = databaseReference.push().key.toString()
         listModel.id = keyId

         return databaseReference.child(
             keyId
         ).setValue(listModel)
     }

    fun update(key : String?, list: HashMap<String, Any>): Task<Void> {
        return databaseReference.child(key.toString()).updateChildren(list)
    }
    fun remove(key : String) : Task<Void> {
        return databaseReference.child(key).removeValue()

    }
}
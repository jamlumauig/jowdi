package jcb.bb.jowdi.database

import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import jcb.bb.jowdi.Views.Model.NotesModel

class FirebaseDB {
    lateinit var databaseReference: DatabaseReference

    fun FirebaseDB(): DatabaseReference {
        val database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Jowdi")
        return databaseReference
    }

    fun images(): DatabaseReference {
        val database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("image")
        return databaseReference
    }

    fun reasons(): DatabaseReference {
        val database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("reasons")
        return databaseReference
    }

    fun musics(): DatabaseReference {
        val database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("music")
        return databaseReference
    }


    fun add(listModel: NotesModel): Task<Void> {
        val keyId = databaseReference.push().key.toString()
        listModel.id = keyId

        return databaseReference.child(
            keyId
        ).setValue(listModel)
    }

    fun update(key: String?, list: HashMap<String, Any>): Task<Void> {
        return databaseReference.child(key.toString()).updateChildren(list)
    }

    fun remove(key: String): Task<Void> {
        return databaseReference.child(key).removeValue()

    }
}
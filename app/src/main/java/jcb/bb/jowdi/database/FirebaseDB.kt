package jcb.bb.jowdi.database

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import jcb.bb.jowdi.Views.Model.ListDataModel

class FirebaseDB {
    lateinit var databaseReference : DatabaseReference

    fun FirebaseDB(){
        val database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Jowdi")
    }
 fun add(listDataModel: ListDataModel): Task<Void> {
     return databaseReference.push().setValue(listDataModel)
 }

    fun update(key : String, hashMap: HashMap<String, Any>): Task<Void> {
        return databaseReference.child(key).updateChildren(hashMap)
    }
    fun remove(key : String) : Task<Void> {
        return databaseReference.child(key).removeValue()
    }

    fun get() : Query {
        return databaseReference.orderByKey()
    }
}
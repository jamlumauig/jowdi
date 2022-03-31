package jcb.bb.jowdi.Views.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import jcb.bb.jowdi.database.UserDb
import jcb.bb.jowdi.ApiConnection.UserRepository
import jcb.bb.jowdi.Views.Model.ListDataModel

class ViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<ListDataModel>>

    private val repository: UserRepository
    lateinit var title :String
    lateinit var desc :String

    init {
        val userDao = UserDb.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.getAlldata
    }

    fun addNote(title: String, desc: String) {
        val param = ListDataModel(
            id = null,
            title = title,
            desc = desc,
            image = "",
            category = "notes"
        )
        repository.insertNote(param)
    }
    operator fun invoke(note: ListDataModel) {
        repository.deleteNote(note)
    }
    operator fun invoke(id: Int): ListDataModel {
        return repository.getNoteById(id)
    }

    fun addContacts(note: ListDataModel) {
        note.id = null
        note.title = title
        note.desc = desc
        note.category = "notes"
        note.image = ""
        repository.insertNote(note)
    }
}
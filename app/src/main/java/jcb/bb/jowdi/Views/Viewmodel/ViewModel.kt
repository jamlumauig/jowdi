package jcb.bb.jowdi.Views.Viewmodel

import android.app.Application
import android.content.ContentValues
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import jcb.bb.jowdi.database.UserDb
import jcb.bb.jowdi.ApiConnection.UserRepository
import jcb.bb.jowdi.Views.Model.ListDataModel
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<ListDataModel>>

    private val repository: UserRepository
    lateinit var title :String
    lateinit var desc :String
    private val userDao = UserDb.getDatabase(application).userDao()

    init {
        repository = UserRepository(userDao)
        readAllData = repository.getAlldata
    }


    fun onClearData(note: Int) {
        viewModelScope.launch {
            clearBookmarks(note)
        }
    }

    private suspend fun clearBookmarks(note: Int) {
        userDao.clearDetails(note)
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

    fun updateNote(id: Int, title: String, desc: String) {
        val param = ListDataModel(
            id = id,
            title = title,
            desc = desc,
            image = "",
            category = "notes"
        )
        repository.insertNote(param)
    }

    fun delete(id: Int){
        repository.delete(id)
    }
    operator fun invoke(note: ListDataModel) {
        repository.deleteNote(note)
    }
    operator fun invoke(id: Int): ListDataModel {
        return repository.getNoteById(id)
    }



}
package jcb.bb.jowdi.Views.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import jcb.bb.jowdi.database.UserDb
import jcb.bb.jowdi.ApiConnection.UserRepository
import jcb.bb.jowdi.Views.Model.ListDataModel

class ViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<ListDataModel>>
    var insertdata: LiveData<List<ListDataModel>>
    private val repository: UserRepository

    init {
        val userDao = UserDb.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.getAlldata
        insertdata = repository.insertData
    }
}
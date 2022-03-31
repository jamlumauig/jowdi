package jcb.bb.jowdi.ApiConnection

import androidx.lifecycle.LiveData
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.database.UserDao

class UserRepository(private val userDao: UserDao) {
    val getAlldata: LiveData<List<ListDataModel>> = userDao.getAllData()

    fun insertNote(note: ListDataModel) = userDao.insertAll(note)

}
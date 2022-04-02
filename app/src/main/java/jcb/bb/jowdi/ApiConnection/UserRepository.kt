package jcb.bb.jowdi.ApiConnection

import android.content.ContentValues
import androidx.lifecycle.LiveData
import jcb.bb.jowdi.Views.Model.ListDataModel
import jcb.bb.jowdi.database.UserDao

class UserRepository(private val userDao: UserDao) {
    val getAlldata: LiveData<List<ListDataModel>> = userDao.getAllData()

    fun insertNote(note: ListDataModel) = userDao.insertAll(note)

     fun getNoteById(id: Int): ListDataModel= userDao.fetchData(id)

     fun deleteNote(note: ListDataModel)= userDao.delete(note)

    fun delete(id: Int) = userDao.deleteById(id)


}
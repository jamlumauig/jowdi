package jcb.bb.jowdi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import jcb.bb.jowdi.Views.Model.ListDataModel

@Dao
interface UserDao {
    @Query("SELECT * FROM datamodels")
    fun getAllData(): LiveData<List<ListDataModel>>
}
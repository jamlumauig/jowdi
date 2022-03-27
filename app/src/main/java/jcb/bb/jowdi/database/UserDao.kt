package jcb.bb.jowdi.database

import androidx.lifecycle.LiveData
import androidx.room.*
import jcb.bb.jowdi.Views.Model.ListDataModel

@Dao
interface UserDao {
    @Query("SELECT * FROM datamodels")
    fun getAllData(): LiveData<List<ListDataModel>>

    // insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: LiveData<List<ListDataModel>>)

    // select ID
    @Query("select * from datamodels where id=:id")
    fun fetchData(id:Int):ListDataModel

    @Delete
    fun deleteData(data:ListDataModel):Int

}
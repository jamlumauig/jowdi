package jcb.bb.jowdi.database

import android.content.ContentValues
import androidx.lifecycle.LiveData
import androidx.room.*
import jcb.bb.jowdi.Views.Model.ListDataModel

@Dao
interface UserDao {
    @Query("SELECT * FROM datamodels")
    fun getAllData(): LiveData<List<ListDataModel>>

    @Query("select * from datamodels where id=:id")
    fun fetchData(id:Int):ListDataModel

    @Delete
    fun deleteData(data:ListDataModel):Int
    
    @Query("SELECT * FROM datamodels")
    fun getAll(): List<ListDataModel>

    @Query("SELECT * FROM datamodels WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<ListDataModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: ListDataModel)

    @Delete
    fun delete(user: ListDataModel)

    @Query("DELETE FROM datamodels WHERE id = :id")
    fun deleteById(id: Int)

    @Query("DELETE FROM datamodels WHERE id = :userIds")
    suspend fun clearDetails(userIds: Int): List<ListDataModel>

}
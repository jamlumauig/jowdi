package jcb.bb.jowdi.database

import android.content.ContentValues
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jcb.bb.jowdi.Views.Model.ListDataModel

@Database(entities = [ListDataModel::class], version = 1, exportSchema = false)
abstract class UserDb : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        @Volatile
         var INSTANCE: UserDb? = null

        fun getDatabase(context: Context): UserDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDb::class.java,
                    "datamodel.db"
                ).createFromAsset("databases/datamodel.db")
                    .allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }



}
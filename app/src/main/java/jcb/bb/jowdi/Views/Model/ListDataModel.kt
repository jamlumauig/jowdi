package jcb.bb.jowdi.Views.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "datamodels")
data class ListDataModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val desc: String,
    val category: String,
    val image: String
) {

}
package jcb.bb.jowdi.Views.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "datamodels")
data class ListDataModel(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    var title: String,
    var desc: String,
    var category: String,
    var image: String
) {

}
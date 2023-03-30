package com.example.mynotes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class data(
    @PrimaryKey(autoGenerate = true)
   val id:Long,
   val text:String,
   val title:String,
   val date:String,
   val time:String,
   val day:String
)

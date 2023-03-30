package com.example.mynotes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface dataDao {
    @Insert
    suspend fun insertData(data:data)

    @Update
    suspend fun updateData(data:data)


    @Query("DELETE  FROM data WHERE id=:qid")
    suspend fun delete_data(qid: Long)
    @Query("UPDATE data  SET title=:title,text=:text,date=:date,time=:time WHERE id=:qid")
    suspend fun update_data(qid: Long,title:String,text:String,date:String,time:String)
    @Query("SELECT * FROM data")
    fun getData(): LiveData<List<data>>
}
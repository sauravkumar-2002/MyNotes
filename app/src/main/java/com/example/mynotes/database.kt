package com.example.mynotes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [data::class], version = 1)
abstract class database  : RoomDatabase(){
    abstract fun datadao():dataDao

    companion object{
        @Volatile
        private  var inst:database?=null
        fun getDatabase(context:Context):database{
            if(inst==null){
                synchronized(this){
                    inst=Room.databaseBuilder(context.applicationContext,
                        database::class.java,"contactDb").build()
                }
            }
            return inst!!
        }
    }
}
package com.example.myapplication.Data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [UserData::class], version = 1, exportSchema = false)
abstract class UserDataBase :RoomDatabase()
{
    abstract fun userDao():UserDao

    companion object{
        @Volatile
        var INSTANCE: UserDataBase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDataBase(context: Context): UserDataBase
        {
            val tempInstance = INSTANCE
            if(tempInstance!=null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDataBase::class.java,
                    "user_likes"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
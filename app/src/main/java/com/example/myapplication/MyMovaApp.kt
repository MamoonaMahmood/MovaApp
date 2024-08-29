package com.example.myapplication

import android.app.Application
import android.content.Context
import com.example.myapplication.Data.UserDao
import com.example.myapplication.Data.UserDataBase

class MyMovaApp: Application() {

    val dataBase by lazy {
        UserDataBase.getDataBase(applicationContext)
    }


    companion object{
        private lateinit var instance: MyMovaApp

        // Function to get the application instance
        private fun getInstance(): MyMovaApp {
            return instance
        }

        // Function to get the UserDao instance
        fun getUserDao(): UserDao {
            return getInstance().dataBase.userDao()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
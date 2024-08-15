package com.example.myapplication.Repository

import androidx.lifecycle.LiveData
import com.example.myapplication.Data.UserDao
import com.example.myapplication.Data.UserData
import kotlinx.coroutines.flow.Flow

class DataBaseRepo(private val userDao: UserDao) {

    val readAllData: Flow<List<UserData>> = userDao.readAllData()

    suspend fun addUserLike( userData: UserData) = userDao.addLikes(userData)
}
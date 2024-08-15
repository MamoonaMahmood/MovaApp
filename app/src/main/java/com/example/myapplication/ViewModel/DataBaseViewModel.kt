package com.example.myapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Data.UserDao
import com.example.myapplication.Data.UserData
import com.example.myapplication.Data.UserDataBase
import com.example.myapplication.Repository.DataBaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DataBaseViewModel(application: Application): AndroidViewModel(application)
{
    private val readAllData: Flow<List<UserData>>
    private val repository: DataBaseRepo

    init {
        val userDao = UserDataBase.getDataBase(application).userDao()

        repository = DataBaseRepo(userDao)
        readAllData = repository.readAllData

    }
    fun addUserLikes(userData: UserData)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserLike(userData)
        }
    }
}
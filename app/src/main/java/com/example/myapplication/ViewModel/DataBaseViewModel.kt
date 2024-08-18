package com.example.myapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.Data.UserData
import com.example.myapplication.Data.UserDataBase
import com.example.myapplication.Repository.DataBaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DataBaseViewModel(application: Application): AndroidViewModel(application)
{
    private var dataBaseRepo: DataBaseRepo

    private val repository: DataBaseRepo

    init {
        val userDao = UserDataBase.getDataBase(application).userDao()
        dataBaseRepo = DataBaseRepo(userDao)

        repository = DataBaseRepo(userDao)
    }

    val readAllDataFlow: Flow<PagingData<UserData>> = repository.readPagedData().cachedIn(viewModelScope)
    fun addUserLikes(userData: UserData)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserLike(userData)
        }
    }
}
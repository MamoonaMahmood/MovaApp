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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DataBaseViewModel(application: Application): AndroidViewModel(application)
{
    private val repository: DataBaseRepo

    private val _isTableEmpty =  MutableStateFlow<Boolean>(false)
    val isTableEmpty = _isTableEmpty

    init {
        val userDao = UserDataBase.getDataBase(application).userDao()

        repository = DataBaseRepo(userDao)
    }

    val readAllDataFlow: Flow<PagingData<UserData>> = repository.readPagedData().cachedIn(viewModelScope)
    fun addUserLikes(userData: UserData)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserLike(userData)
        }
    }

    fun deleteUserLike(userData: UserData)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(userData)
        }
    }

    fun deleteAllUsers()
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }

    fun checkTableEmpty()
    {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.checkIfTableisEmpty()
            _isTableEmpty.value = result
        }
    }
}
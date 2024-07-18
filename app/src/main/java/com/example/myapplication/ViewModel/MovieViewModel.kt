package com.example.myapplication.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ImageLoad
import kotlinx.coroutines.launch

class MovieViewModel: ViewModel() {
    val movieMutableLiveData: MutableLiveData<List<ImageLoad>> = MutableLiveData()

    fun getMovie()
    {
        viewModelScope.launch {  }
    }

}
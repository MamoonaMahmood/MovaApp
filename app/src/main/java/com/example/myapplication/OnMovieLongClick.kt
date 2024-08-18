package com.example.myapplication

import com.example.myapplication.Data.MovieResult
import com.example.myapplication.Data.UserData

interface OnMovieLongClickListener {
    fun onMovieLongClicked(movieResult: MovieResult)
}
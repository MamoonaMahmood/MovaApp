package com.example.myapplication.CallbackInterfaces

import com.example.myapplication.Data.MovieResult

interface OnMovieLongClickListener {
    fun onMovieLongClicked(movieResult: MovieResult)
}
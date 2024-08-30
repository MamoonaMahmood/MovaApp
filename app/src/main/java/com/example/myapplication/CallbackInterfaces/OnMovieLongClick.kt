package com.example.myapplication.CallbackInterfaces

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.Data.MovieResult
import com.example.myapplication.Data.UserData
import com.example.myapplication.ViewModel.NewMovieViewModel

class OnMovieLongClickListener(
    private val newMovieViewModel: NewMovieViewModel,
    private val context: Context
) {
    fun onMovieLongClicked(movieResult: MovieResult) {
        showDialogueBox(
            onPositiveClick = {
                val userData = UserData(
                    id = 0,
                    posterPath = movieResult.posterPath,
                    voteAverage = movieResult.voteAverage
                )
                newMovieViewModel.addUserLikes(userData)
                Toast.makeText(context, "Added to Favourites", Toast.LENGTH_SHORT).show()
            },
            onNegativeClick = {
                Toast.makeText(context, "Not Added to Favourites", Toast.LENGTH_SHORT).show()
            }, context = context)
    }
        private fun showDialogueBox(onPositiveClick: () -> Unit, onNegativeClick: () -> Unit, context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmation")
            builder.setMessage("Do you want to add this title to favorites?")

            builder.setPositiveButton("Yes") { _, _ ->
                onPositiveClick()
            }

            builder.setNegativeButton("No") { _, _ ->
                onNegativeClick()
            }
            builder.show()
        }

    }
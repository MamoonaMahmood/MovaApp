package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CallbackInterfaces.OnMovieLongClickListener
import com.example.myapplication.Data.MovieResult
import com.example.myapplication.Data.UserData
import com.example.myapplication.ViewModel.DataBaseViewModel
import com.example.myapplication.ViewModel.NewMovieViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewReleasesSeeAll : Fragment(R.layout.fragment_new_releases_see_all),
    OnMovieLongClickListener {

    private lateinit var recyclerView : RecyclerView
    private lateinit var newReleaseAdapter: MoviePagingAdapter
    private lateinit var dbViewModel: DataBaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.hasFixedSize()


        newReleaseAdapter = MoviePagingAdapter(this)
        recyclerView.adapter = newReleaseAdapter

        val newMovieViewModel = NewMovieViewModel()
        dbViewModel = ViewModelProvider(this)[DataBaseViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            newMovieViewModel.upComingMovies.collectLatest { pagingData ->
                newReleaseAdapter.submitData(pagingData)
            }
        }
    }
    override fun onMovieLongClicked(movieResult: MovieResult) {
        showDialogueBox(
            onPositiveClick = {
            val userData = UserData(
                id = 0 ,
                posterPath = movieResult.posterPath,
                voteAverage = movieResult.voteAverage
            )

            dbViewModel.addUserLikes(userData)
            Toast.makeText(context, "Added to Favourites", Toast.LENGTH_SHORT).show()
        },
        onNegativeClick = {
            Toast.makeText(context, "Not Added to Favourites", Toast.LENGTH_SHORT).show()
        })
    }

    private fun showDialogueBox(onPositiveClick: () -> Unit, onNegativeClick: () -> Unit)
    {
        val builder =  AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmation")
        builder.setMessage("Do you want to add this title to favorites?")

        builder.setPositiveButton("Yes"){ _, _ ->
            onPositiveClick()
        }

        builder.setNegativeButton("No"){ _,_ ->
            onNegativeClick()
        }
        builder.show()
    }

}
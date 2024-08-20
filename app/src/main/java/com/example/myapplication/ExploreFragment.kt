package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.myapplication.ViewModel.NewMovieViewModel

class ExploreFragment : Fragment(R.layout.fragment_explore), OnMovieLongClickListener {


    private lateinit var popRecyclerView: RecyclerView
    private lateinit var filterRecyclerView: RecyclerView
    private lateinit var errorImage: ImageView
    private lateinit var filterBtn: ImageButton
    private lateinit var searchView: SearchView
    private lateinit var dbViewModel: DataBaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filterBtn = view.findViewById(R.id.imageFilterButton)
        searchView = view.findViewById(R.id.searchView)
        errorImage = view.findViewById(R.id.errorImage)
        errorImage.visibility = GONE


        popRecyclerView = view.findViewById(R.id.popRecyclerView)
        filterRecyclerView = view.findViewById(R.id.filterRecyclerView)

        filterRecyclerView.layoutManager = GridLayoutManager(context, 2)
        popRecyclerView.layoutManager = GridLayoutManager(context, 2)

        filterRecyclerView.hasFixedSize()
        popRecyclerView.hasFixedSize()


        val filterPagingAdapter = MoviePagingAdapter(this)
        filterRecyclerView.adapter = filterPagingAdapter
        val popMoviePagingAdapter = MoviePagingAdapter(this)
        popRecyclerView.adapter = popMoviePagingAdapter


        val newMovieViewModel = ViewModelProvider(requireActivity())[NewMovieViewModel::class.java]
        dbViewModel = ViewModelProvider(this)[DataBaseViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            newMovieViewModel.moviesFlow.collectLatest { pagingData ->
                popMoviePagingAdapter.submitData(pagingData)

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            newMovieViewModel.filterMoviesFlow.collectLatest { pagingData->
                filterPagingAdapter.submitData(pagingData)
                popRecyclerView.visibility = INVISIBLE
                filterRecyclerView.visibility = VISIBLE
            }

        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("SearchView", "Query submitted: $query")
                newMovieViewModel.updateSearchQuery(query)
                popRecyclerView.visibility = VISIBLE
                filterRecyclerView.visibility = INVISIBLE
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("SearchView", "Query submitted: $newText")
                newMovieViewModel.updateSearchQuery(newText)
                popRecyclerView.visibility = VISIBLE
                filterRecyclerView.visibility = INVISIBLE
                return true
            }
        })

        filterBtn.setOnClickListener{
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(childFragmentManager, BottomSheetFragment.TAG)

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
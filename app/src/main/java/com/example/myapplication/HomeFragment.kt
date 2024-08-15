package com.example.myapplication


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.MovieResult
import com.example.myapplication.Data.UserData
import com.example.myapplication.ViewModel.DataBaseViewModel
import com.example.myapplication.ViewModel.NewMovieViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home), OnMovieLongClickListener {


    private lateinit var topTenPagingAdapter: MoviePagingAdapter
    private lateinit var newReleasePagingAdapter: MoviePagingAdapter
    private lateinit var dbViewModel: DataBaseViewModel
    private lateinit var topTenRecyclerView: RecyclerView
    private lateinit var newReleaseRecyclerView: RecyclerView
    private lateinit var seeAllTopMovies : TextView
    private lateinit var seeAllTopRelease : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seeAllTopMovies = view.findViewById(R.id.seeAllTopMovie)
        seeAllTopRelease = view.findViewById(R.id.seeAllNewMovie)

        seeAllTopMovies.setOnClickListener{

            val nextTransaction = requireActivity().supportFragmentManager.beginTransaction()
            nextTransaction.replace(R.id.fragmentContainerView, TopMoviesSeeAll())
                .addToBackStack(null)
                .commit()
        }

        seeAllTopRelease.setOnClickListener{

            val nextTransaction = requireActivity().supportFragmentManager.beginTransaction()
            nextTransaction.replace(R.id.fragmentContainerView, NewReleasesSeeAll())
                .addToBackStack(null)
                .commit()
        }


        //Initialize both recyclerViews
        initializeRecyclerView(view)

        dbViewModel = ViewModelProvider(this)[DataBaseViewModel::class.java]
        val newMovieViewModel = NewMovieViewModel()

        viewLifecycleOwner.lifecycleScope.launch {
            newMovieViewModel.topRatedMoviesFlow.collectLatest { pagingData ->
                topTenPagingAdapter.submitData(pagingData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            newMovieViewModel.upComingMovies.collectLatest { pagingData ->
                newReleasePagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun initializeRecyclerView(view: View): View {
        topTenRecyclerView = view.findViewById(R.id.recyclerView1)
        newReleaseRecyclerView = view.findViewById(R.id.recyclerView2)

        topTenRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topTenRecyclerView.hasFixedSize()

        newReleaseRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topTenRecyclerView.hasFixedSize()

        topTenPagingAdapter = MoviePagingAdapter(this)
        newReleasePagingAdapter = MoviePagingAdapter(this)

        topTenRecyclerView.adapter = topTenPagingAdapter
        newReleaseRecyclerView.adapter = newReleasePagingAdapter

        return view
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



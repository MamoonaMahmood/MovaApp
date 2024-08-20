package com.example.myapplication


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.CallbackInterfaces.OnMovieLongClickListener
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
    private lateinit var mainImageView: ImageView
    private lateinit var bannerNameTextView: TextView

    private var currentBannerIndex = 0
    private val bannerChangeHandler = Handler(Looper.getMainLooper())
    private val bannerChangeRunnable = object : Runnable {
        override fun run() {
            updateBanner()
            bannerChangeHandler.postDelayed(this, 5000) // Change banner every 3 seconds
        }
    }

    private var moviesList: List<MovieResult> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seeAllTopMovies = view.findViewById(R.id.seeAllTopMovie)
        seeAllTopRelease = view.findViewById(R.id.seeAllNewMovie)
        mainImageView = view.findViewById(R.id.mainImage)
        bannerNameTextView = view.findViewById(R.id.doctorText)

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

        newMovieViewModel.fetchBannerMovies()

        viewLifecycleOwner.lifecycleScope.launch {
            newMovieViewModel.bannerMoviesFlow.collectLatest { response ->
                response?.results?.let { movies ->
                    if (movies.isNotEmpty()) {
                        moviesList = movies
                        currentBannerIndex = 0
                        bannerChangeHandler.removeCallbacks(bannerChangeRunnable)
                        bannerChangeRunnable.run() // Start the banner change runnable
                    }
                }
            }
        }

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

    private fun updateBanner() {
        if (moviesList.isNotEmpty()) {
            val movie = moviesList[currentBannerIndex]
            Glide.with(this@HomeFragment)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .centerCrop()
                .into(mainImageView)

            bannerNameTextView.text = movie.title

            currentBannerIndex = (currentBannerIndex + 1) % moviesList.size
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        bannerChangeHandler.removeCallbacks(bannerChangeRunnable)
    }
}



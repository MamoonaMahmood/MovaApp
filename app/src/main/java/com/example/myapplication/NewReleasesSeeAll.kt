package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CallbackInterfaces.OnMovieLongClickListener
import com.example.myapplication.CallbackInterfaces.onMovieLongClick
import com.example.myapplication.Data.MovieResult
import com.example.myapplication.Data.UserData
import com.example.myapplication.ViewModel.NewMovieViewModel
import com.example.myapplication.adapter.MoviePagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewReleasesSeeAll : Fragment(R.layout.fragment_new_releases_see_all) {
    private lateinit var movieLongClickListener: OnMovieLongClickListener
    private lateinit var backBtn: ImageButton
    private lateinit var recyclerView : RecyclerView
    private lateinit var newReleaseAdapter: MoviePagingAdapter
    private lateinit var newMovieViewModel: NewMovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backBtn = view.findViewById(R.id.backBtn)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.hasFixedSize()

        newMovieViewModel = ViewModelProvider(requireActivity())[NewMovieViewModel::class.java]
        movieLongClickListener = OnMovieLongClickListener(newMovieViewModel, requireContext())
        newReleaseAdapter = MoviePagingAdapter(movieLongClickListener)


        recyclerView.adapter = newReleaseAdapter

        backBtn.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }


        viewLifecycleOwner.lifecycleScope.launch {
            newMovieViewModel.upComingMovies.collectLatest { pagingData ->
                newReleaseAdapter.submitData(pagingData)
            }
        }
    }

}
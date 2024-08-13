package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.MovieResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.myapplication.ViewModel.NewMovieViewModel
import kotlinx.coroutines.flow.flatMapLatest


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [exploreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class exploreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var popRecyclerView: RecyclerView
    private lateinit var filterRecyclerView: RecyclerView
    private lateinit var errorImage: ImageView
    private lateinit var filterBtn: ImageButton
    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

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


        val filterPagingAdapter = MoviePagingAdapter()
        filterRecyclerView.adapter = filterPagingAdapter
        val popMoviePagingAdapter = MoviePagingAdapter()
        popRecyclerView.adapter = popMoviePagingAdapter


        val newMovieViewModel = ViewModelProvider(requireActivity())[NewMovieViewModel::class.java]

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
                searchView.clearFocus() // Optionally clear focus after submission
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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filterBtn.setOnClickListener{
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(childFragmentManager, BottomSheetFragment.TAG)


        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment exploreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            exploreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
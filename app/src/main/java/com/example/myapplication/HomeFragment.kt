package com.example.myapplication

import com.example.myapplication.Repository.MovieRepoWithPaging
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Network.ApiRequestHandle
import com.example.myapplication.Network.RetrofitBuilder
import com.example.myapplication.ViewModel.NewMovieViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var topTenPagingAdapter: MoviePagingAdapter
    private lateinit var newReleasePagingAdapter: MoviePagingAdapter

    private lateinit var topTenRecyclerView: RecyclerView
    private lateinit var newReleaseRecyclerView: RecyclerView
    private lateinit var seeAllTopMovies : TextView
    private lateinit var seeAllTopRelease : TextView



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
        val view = inflater.inflate(R.layout.fragment_home, container, false)
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
        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
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

        topTenPagingAdapter = MoviePagingAdapter()
        newReleasePagingAdapter = MoviePagingAdapter()

        topTenRecyclerView.adapter = topTenPagingAdapter
        newReleaseRecyclerView.adapter = newReleasePagingAdapter

        return view
    }
}



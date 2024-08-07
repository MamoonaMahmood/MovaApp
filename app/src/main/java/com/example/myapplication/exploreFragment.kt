package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.myapplication.ViewModel.NewMovieViewModel


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
    private lateinit var searchRecyclerView: RecyclerView
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

        filterBtn.setOnClickListener{
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(childFragmentManager, BottomSheetFragment.TAG)
        }

        popRecyclerView = view.findViewById(R.id.recyclerView3)
        popRecyclerView.layoutManager = GridLayoutManager(context, 2)
        popRecyclerView.hasFixedSize()



        val moviePagingAdapter = MoviePagingAdapter()
        popRecyclerView.adapter = moviePagingAdapter

        val newMovieViewModel = NewMovieViewModel()

        viewLifecycleOwner.lifecycleScope.launch {
            newMovieViewModel.moviesFlow.collectLatest { pagingData ->
                moviePagingAdapter.submitData(pagingData)
            }
        }



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("SearchView", "Query submitted: $query")
                newMovieViewModel.updateSearchQuery(query)
                searchView.clearFocus() // Optionally clear focus after submission
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("SearchView", "Query submitted: $newText")
                newMovieViewModel.updateSearchQuery(newText)
                return true
            }
        })


        return view
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
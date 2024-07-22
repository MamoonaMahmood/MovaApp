package com.example.myapplication

import MovieResponse
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

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

    private lateinit var adapter1: CustomAdapter
    private lateinit var adapter2: CustomAdapter

    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var imageList: ArrayList<ImageLoad>

    private lateinit var movieRating: Array<String>
    private lateinit var movieImage: Array<Int>
    private lateinit var imageLoadList: ArrayList<ImageLoad>
    private lateinit var movieViewModel: MovieViewModel
    private val apiRequestHandle = RetrofitBuilder.instance.create(ApiRequestHandle::class.java)


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

        //Initialize both recyclerViews
        initializeRecyclerView(view)

        //initialize viewModel for api Requests
        val movieViewModel: MovieViewModel by viewModels {
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        }


        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.movieStateFlow.collect { movieResponse ->
                movieResponse?.let { response ->
                    // Handle successful response
                    adapter1.onSuccessPopulate(movieResponse)

                } ?: run {
                    // Handle null response or error state
                    Log.d("Home Fragment", "Error in API call or null response")
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.newMovieStateFlow.collect { movieResponse ->
                movieResponse?.let { response ->
                    // Handle successful response
                    adapter2.onSuccessPopulate(response)

                } ?: run {
                    // Handle null response or error state
                    Log.d("Home Fragment", "Error in API call or null response")
                }
            }
        }

        // Trigger the API call to fetch movie data
        movieViewModel.fetchMovie()
        movieViewModel.fetchUpcomingMovies()

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
        recyclerView1 = view.findViewById(R.id.recyclerView1)
        recyclerView2 = view.findViewById(R.id.recyclerView2)

        recyclerView1.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView1.hasFixedSize()

        recyclerView2.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView1.hasFixedSize()


        imageList = arrayListOf<ImageLoad>()
        adapter1 = CustomAdapter(imageList, requireContext())
        adapter2 = CustomAdapter(imageList, requireContext())

        recyclerView1.adapter = adapter1
        recyclerView2.adapter = adapter2

        return view
    }
}
/*Parsing the api result list to get needed items*/
//    private fun onSuccess(movieResponse: MovieResponse) {
//        imageLoadList = arrayListOf<ImageLoad>()
//        movieResponse.results
//        for (result in movieResponse.results) {
//
//            val imageUrl = "https://image.tmdb.org/t/p/w500${result.posterPath}"
//            val rating = result.voteAverage.toFloat() // Convert to integer rating
//
//            val imageLoad = ImageLoad(imageUrl, rating)
//            imageLoadList.add(imageLoad)
//        }
//
//    }

/*Dummy function to check recycler view and adapter attachment*/

//    private fun initializeData()
//    {
//        imageList = arrayListOf<ImageLoad>()
//        movieRating = arrayOf( getString(R.string.dummyRating), getString(R.string.dummyRating))
//        movieImage = arrayOf(R.drawable.image, R.drawable.image)

//        for (i in movieImage.indices)
//        {
//            val newItem = ImageLoad(movieRating[i], movieImage[i])
//            imageList.add(newItem)
//        }

//}
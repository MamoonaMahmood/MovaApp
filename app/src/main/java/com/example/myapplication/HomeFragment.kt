package com.example.myapplication

import MovieResponse
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private lateinit var adapter: CustomAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageList: ArrayList<ImageLoad>

    private lateinit var movieRating: Array<String>
    private lateinit var movieImage: Array<Int>
    private lateinit var imageLoadList : ArrayList<ImageLoad>
    private val apiRequestHandle = RetrofitBuilder.instance.create(ApiRequestHandle::class.java)
    private val apiKey="316373081224cd654e971158dc41dc51"


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

        initializeData()
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.hasFixedSize()

        adapter = CustomAdapter(imageList, requireContext())
        recyclerView.adapter = adapter

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val movieResponse = apiRequestHandle.getMovie(apiKey)
                onSuccess(movieResponse)

                imageLoadList?.let {
                    adapter.populate(it)
                }
            }
            catch (e: Exception)
            {
                Log.d("Home Fragment", "Error in api call")
            }

        }


        adapter.populate(imageList)

        return view
    }

    private fun onSuccess(movieResponse: MovieResponse) {
        imageLoadList = arrayListOf<ImageLoad>()
        movieResponse.results
        for (result in movieResponse.results) {

            val imageUrl = "https://image.tmdb.org/t/p/w500${result.posterPath}"
            val rating = result.voteAverage.toFloat() // Convert to integer rating

            val imageLoad = ImageLoad(imageUrl, rating)
            imageLoadList.add(imageLoad)
        }

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
    private fun initializeData()
    {
        imageList = arrayListOf<ImageLoad>()
        movieRating = arrayOf( getString(R.string.dummyRating), getString(R.string.dummyRating))
        movieImage = arrayOf(R.drawable.image, R.drawable.image)

//        for (i in movieImage.indices)
//        {
//            val newItem = ImageLoad(movieRating[i], movieImage[i])
//            imageList.add(newItem)
//        }

    }

}
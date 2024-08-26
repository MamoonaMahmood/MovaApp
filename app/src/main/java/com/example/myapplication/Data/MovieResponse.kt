package com.example.myapplication.Data

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val results: List<MovieResult>
)

data class MovieResult(
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("title")
    val title: String,

)

data class FilterObj(
    val regionString: String = "",
    val sortString: String = "",
    val genreString: String = "",
    val timeString: String =""
){
    companion object {
        fun default() = FilterObj()
    }
}
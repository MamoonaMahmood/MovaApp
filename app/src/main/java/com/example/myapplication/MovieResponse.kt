import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val results: List<MovieResult>
)

data class MovieResult(
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double
) {
    fun imageUrl() = "wwwww/fgh/gfh${posterPath}"
}

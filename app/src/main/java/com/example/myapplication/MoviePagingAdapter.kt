package com.example.myapplication

import MovieResult
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.IndividualMovieItemBinding

class MoviePagingAdapter : PagingDataAdapter<MovieResult , MoviePagingAdapter.MovieViewHolder>(MovieResultDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviePagingAdapter.MovieViewHolder {
        val binding = IndividualMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviePagingAdapter.MovieViewHolder, position: Int) {
        val movieResult = getItem(position)
        if (movieResult != null) {
            holder.bind(movieResult)
        }
    }

    class MovieViewHolder(private val binding: IndividualMovieItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(movieResult : MovieResult)
        {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movieResult.posterPath}")
                .into(binding.movieItem)

            binding.ratingItem.text = movieResult.voteAverage.toString()
        }
    }
    class MovieResultDiffCallback : DiffUtil.ItemCallback<MovieResult>() {
        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            // Assuming posterPath uniquely identifies the item
            return oldItem.posterPath == newItem.posterPath
        }

        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            // Check if all contents are the same
            return oldItem == newItem
        }
    }

}
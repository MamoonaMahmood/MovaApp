package com.example.myapplication.adapter


import com.example.myapplication.Data.MovieResult
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.CallbackInterfaces.OnMovieLongClickListener
import com.example.myapplication.databinding.IndividualMovieItemBinding


class MoviePagingAdapter(
    private val onMovieLongClickListener: OnMovieLongClickListener
) : PagingDataAdapter<MovieResult, MoviePagingAdapter.MovieViewHolder>(MovieResultDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding = IndividualMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onMovieLongClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieResult = getItem(position)
        if (movieResult != null) {
            holder.bind(movieResult)
        }
    }

    fun isEmpty(): Boolean {
        // Return true if the current data is empty
        return itemCount == 0
    }

    class MovieViewHolder(
        private val binding: IndividualMovieItemBinding,
        private val onMovieLongClickListener: OnMovieLongClickListener
    )
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieResult : MovieResult)
        {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movieResult.posterPath}")
                .into(binding.movieItem)
            binding.ratingItem.text = movieResult.voteAverage.toString()
            binding.addButtonItem.setOnClickListener {
                onMovieLongClickListener.onMovieLongClicked(movieResult)
            }
        }
    }
    class MovieResultDiffCallback : DiffUtil.ItemCallback<MovieResult>() {
        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            // Assuming title uniquely identifies the item
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            // Check if all contents are the same
            return oldItem == newItem
        }
    }

}
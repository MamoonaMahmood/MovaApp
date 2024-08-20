package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.CallbackInterfaces.onMovieLongClick
import com.example.myapplication.Data.UserData
import com.example.myapplication.databinding.IndividualMovieItemBinding

class DatabasePagingAdapter(
    private val onMovieLongClick: onMovieLongClick
): PagingDataAdapter<UserData, DatabasePagingAdapter.DatabaseViewHolder>(DatabaseResultDiffCallBack())
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DatabaseViewHolder {
        val binding = IndividualMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DatabaseViewHolder(binding, onMovieLongClick )
    }

    override fun onBindViewHolder(holder: DatabaseViewHolder, position: Int) {
        val userLike = getItem(position)
        if(userLike!=null)
        {
            holder.bind(userLike)
        }

    }

    class DatabaseViewHolder(
        private val binding: IndividualMovieItemBinding,
        private val onMovieLongClick: onMovieLongClick
    ): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(userData: UserData)
        {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${userData.posterPath}")
                .into(binding.movieItem)

            binding.ratingItem.text = userData.voteAverage.toString()

            itemView.setOnLongClickListener{
                onMovieLongClick.onMovieLongClicked(userData)
                true
            }
        }
    }

    class DatabaseResultDiffCallBack: DiffUtil.ItemCallback<UserData>()
    {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem == newItem
        }

    }
}
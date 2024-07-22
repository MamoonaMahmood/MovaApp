package com.example.myapplication

import MovieResponse
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.google.android.material.imageview.ShapeableImageView

class CustomAdapter(private var ImageList : ArrayList<ImageLoad>, private val context: Context) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.MyViewHolder {
        //inflates individual cardview holding recycler view items//
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.individual_movie_item,
            parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: CustomAdapter.MyViewHolder, position: Int) {
       val currentItem = ImageList[position]
        holder.apply {
            RatingUrlView.text = currentItem.rating.toString()
            if (currentItem.imageUrl != null) {
                Glide.with(context)
                    .load(currentItem.imageUrl)
                    .into(imageUrlView)
            } else {
                imageUrlView.setImageResource(R.drawable.image)
            }

        }

//        holder.imageUrlView.setImageResource(R.drawable.image)
//        holder.RatingUrlView.text = currentItem.rating.toString()
    }
    fun populate( arrayList: ArrayList<ImageLoad>)
    {
        ImageList = arrayList
        notifyDataSetChanged()
    }

    fun onSuccessPopulate(movieResponse: MovieResponse)
    {
        val imageLoadList = arrayListOf<ImageLoad>()
        movieResponse.results
        for (result in movieResponse.results) {

            val imageUrl = "https://image.tmdb.org/t/p/w500${result.posterPath}"
            val rating = result.voteAverage.toFloat() // Convert to integer rating

            val imageLoad = ImageLoad(imageUrl, rating)
            imageLoadList.add(imageLoad)
        }
        ImageList = imageLoadList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return ImageList.size
    }
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val imageUrlView: ShapeableImageView = itemView.findViewById(R.id.movieItem)
        val RatingUrlView : TextView = itemView.findViewById(R.id.ratingItem)
    }


}
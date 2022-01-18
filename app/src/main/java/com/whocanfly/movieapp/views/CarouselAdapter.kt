package com.whocanfly.movieapp.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jackandphantom.carouselrecyclerview.view.ReflectionImageView
import com.whocanfly.movieapp.R
import com.whocanfly.movieapp.api.Movie
import com.whocanfly.movieapp.api.UpcomingMovie
import com.whocanfly.movieapp.utils.Definitions

class CarouselAdapter(private var moviesList: List<Movie>) :
    RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCarouselItem: ReflectionImageView = itemView.findViewById(R.id.ivCarouselItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(
                R.layout.carousel_item_view, parent, false
            )
        return ViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.ivCarouselItem)
            .load(Definitions.IMAGE_URL_W500 + moviesList.get(position).posterPath)
            .into(holder.ivCarouselItem)
    }

    fun updateData(list: List<Movie>) {
        this.moviesList = list
        notifyDataSetChanged()
    }
}
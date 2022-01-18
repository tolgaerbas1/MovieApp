package com.whocanfly.movieapp.views.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.whocanfly.movieapp.api.Movie
import com.whocanfly.movieapp.databinding.MainMovieItemBinding

class MovieDetailAdapter(private val movieList: List<Movie>) :
    RecyclerView.Adapter<MovieDetailAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(private val movieItemBinding: MainMovieItemBinding) :
        RecyclerView.ViewHolder(movieItemBinding.root) {
        fun bind(movie: Movie) {
            movieItemBinding.item = movie
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MainMovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        viewHolder.apply {
            bind(movie)
            onItemClickListener?.let {
                it(movie)
            }
        }
    }

    override fun getItemCount() = movieList.size

    private var onItemClickListener: ((Movie) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Movie) -> Unit)) {
        onItemClickListener = listener
    }


}